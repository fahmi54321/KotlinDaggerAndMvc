package com.android.mvcdandagger.screens.questionslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.mvcdandagger.R
import com.android.mvcdandagger.questions.Question
import com.android.mvcdandagger.screens.questiondetails.QuestionDetailsActivity

class QuestionsListViewMvc(
    private val layoutInflater: LayoutInflater, //todo 1
    private val parent: ViewGroup? //todo 2
) {

    //todo 7
    interface Listener {
        fun onRefreshClicked()
        fun onQuestionClicked(clickQuestion: Question) //todo 18 (next QuestionsListActivity)
    }

    //todo 3
    private val swipeRefresh: SwipeRefreshLayout
    private val recyclerView: RecyclerView
    private val questionsAdapter: QuestionsAdapter

    //todo 5
    val rootView: View = layoutInflater.inflate(R.layout.activity_questions_list, parent, false)
    private val context: Context get() = rootView.context //todo 20

    //todo 8
    private val listeners = HashSet<Listener>()

    init {

        //todo 4
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {

            //todo 10 (next QuestionsListActivity)
            for (listener in listeners) {
                listener.onRefreshClicked()
            }
        }

        //todo 4
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context) //todo 21
        questionsAdapter = QuestionsAdapter { clickQuestion ->

            //todo 17
            for (listener in listeners) {
                listener.onQuestionClicked(clickQuestion)
            }
        }
        recyclerView.adapter = questionsAdapter
    }

    //todo 28 (finish)
    fun bindQuestions(questions: List<Question>) {
        questionsAdapter.bindData(questions)
    }


    //todo 9
    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    //todo 9
    fun unRegisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    //todo 22
    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    //todo 23 (next QuestionsListActivity)
    fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    //todo 6
    fun <T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }


    //todo 4
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