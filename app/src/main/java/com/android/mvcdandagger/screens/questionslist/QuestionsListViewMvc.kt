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
import com.android.mvcdandagger.screens.common.viewmvc.BaseViewMvc

class QuestionsListViewMvc(
    private val layoutInflater: LayoutInflater, //todo 1
    private val parent: ViewGroup? //todo 2
):BaseViewMvc<QuestionsListViewMvc.Listener>(
    layoutInflater,
    parent,
    R.layout.activity_questions_list
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