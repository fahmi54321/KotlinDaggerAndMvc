package com.android.mvcdandagger.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.mvcdandagger.R
import com.android.mvcdandagger.screens.common.toolbar.MyToolbar
import com.android.mvcdandagger.screens.common.viewmvc.BaseViewMvc

class QuestionDetailsListViewMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
):BaseViewMvc<QuestionDetailsListViewMvc.Listeners>(
    layoutInflater,
    parent,
    R.layout.activity_questions_list
) {

    interface Listeners{
        fun onBackClicked()
    }

    private lateinit var txtQuestionBody: TextView

    var swipeRefresh: SwipeRefreshLayout
    var toolbar: MyToolbar

    init {
        txtQuestionBody = findViewById(R.id.txt_question_body)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            for (listener in listeners){
                listener.onBackClicked()
            }
        }

        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing){
            swipeRefresh.isRefreshing = false
        }
    }

    fun bindQuestions(detailsText: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            txtQuestionBody.text = Html.fromHtml(detailsText, Html.FROM_HTML_MODE_LEGACY)
        }else{
            @Suppress("DEPRECATION")
            txtQuestionBody.text = Html.fromHtml(detailsText)
        }
    }


}