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
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) {

    // todo 1 (buat event)
    interface Listener {
        fun onRefreshClicked()
        fun onQuestionClicked(clickQuestion: Question)
    }

    // todo 2 (deklarasi variabel)
    private val swipeRefresh: SwipeRefreshLayout
    private val recyclerView: RecyclerView
    private val questionsAdapter: QuestionsAdapter
    private val context: Context get() = rootView.context
    private val listeners = HashSet<Listener>()

    val rootView: View = layoutInflater.inflate(R.layout.activity_questions_list, parent, false)

    // todo 3 (init variabel)
    init {

        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {

            for (listener in listeners) {
                listener.onRefreshClicked()
            }
        }

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        questionsAdapter = QuestionsAdapter { clickQuestion ->

            for (listener in listeners) {
                listener.onQuestionClicked(clickQuestion)
            }
        }
        recyclerView.adapter = questionsAdapter
    }

    // todo 4 (buat method / fungsi)
    fun bindQuestions(questions: List<Question>) {
        questionsAdapter.bindData(questions)
    }


    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unRegisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    private fun <T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }


    // todo 5 (buat adapter, next QuestionListActivity)
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