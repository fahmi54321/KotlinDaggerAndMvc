package com.android.mvcdandagger.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.mvcdandagger.R
import com.android.mvcdandagger.screens.common.toolbar.MyToolbar

class QuestionDetailsListViewMvc(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?
) {

    interface Listeners{
        fun onBackClicked()
    }

    private lateinit var txtQuestionBody: TextView

    val rootView:View = layoutInflater.inflate(R.layout.activity_question_details,parent,false)
    var swipeRefresh: SwipeRefreshLayout
    var toolbar: MyToolbar
    val listeners = HashSet<Listeners>()

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

    fun <T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing){
            swipeRefresh.isRefreshing = false
        }
    }

    fun registerListener(listener : Listeners){
        listeners.add(listener)
    }

    fun unRegisterListener(listener: Listeners){
        listeners.remove(listener)
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