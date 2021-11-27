package com.android.mvcdandagger.screens.questionslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.mvcdandagger.Constants
import com.android.mvcdandagger.R
import com.android.mvcdandagger.networking.StackoverflowApi
import com.android.mvcdandagger.questions.Question
import com.android.mvcdandagger.screens.common.dialogs.ServerErrorDialogFragment
import com.android.mvcdandagger.screens.questiondetails.QuestionDetailsActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.start
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionsListActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var stackoverflowApi: StackoverflowApi
    private lateinit var questionsAdapter: QuestionsAdapter
    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_list)

        //init pull-down-to-refresh
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            fetchQuestions()
        }

        //init recycler view
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        questionsAdapter = QuestionsAdapter { clickQuestion ->
            QuestionDetailsActivity.start(this,clickQuestion.id)
        }
        recyclerView.adapter = questionsAdapter

        //init retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        stackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    }

    override fun onStart() {
        super.onStart()
        if (!isDataLoaded){
            fetchQuestions()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestions(){
        coroutineScope.launch {
            showProgressIndication()
            try {
                val response = stackoverflowApi.lastActiveQuestions(20)
                if (response.isSuccessful && response.body() != null){
                    questionsAdapter.bindData(response.body()!!.questions)
                    isDataLoaded = true
                }else{
                    onFetchFailed()
                }
            }catch (t:Throwable){
                onFetchFailed()
            }finally {
                hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed(){
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(),null)
            .commitAllowingStateLoss()
    }

    private fun showProgressIndication(){
        swipeRefresh.isRefreshing = true
    }

    private fun hideProgressIndication(){
        if (swipeRefresh.isRefreshing){
            swipeRefresh.isRefreshing = false
        }
    }

    class QuestionsAdapter(
        private val onQuestionClickListener: (Question) -> Unit
    ) : RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>() {


        private var questionList: List<Question> = ArrayList(0)

        inner class QuestionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.txt_title)
        }

        fun bindData(questions: List<Question>) {
            questionList = ArrayList(questions)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_question_list_item, parent, false)
            return QuestionsViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
            holder.title.text = questionList[position].title
            holder.itemView.setOnClickListener {
                onQuestionClickListener.invoke(questionList[position])
            }
        }

        override fun getItemCount(): Int {
            return questionList.size
        }
    }
}