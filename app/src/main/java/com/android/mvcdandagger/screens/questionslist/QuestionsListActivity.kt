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
import com.android.mvcdandagger.screens.questiondetails.FetchQuestionsUseCase
import com.android.mvcdandagger.screens.questiondetails.QuestionDetailsActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.start
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionsListActivity : AppCompatActivity(), QuestionsListViewMvc.Listener { //todo 11

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var isDataLoaded = false

    private lateinit var viewMvc: QuestionsListViewMvc

    private lateinit var fetchQuestionsUseCase: FetchQuestionsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewMvc = QuestionsListViewMvc(LayoutInflater.from(this), null)

        setContentView(viewMvc.rootView)

        fetchQuestionsUseCase = FetchQuestionsUseCase()

    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        if (!isDataLoaded) {
            fetchQuestions()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        viewMvc.unRegisterListener(this)
    }

    override fun onRefreshClicked() {
        fetchQuestions()
    }

    override fun onQuestionClicked(clickQuestion: Question) {
        QuestionDetailsActivity.start(this, clickQuestion.id)
    }

    private fun fetchQuestions() {
        coroutineScope.launch {
            viewMvc.showProgressIndication()
            //todo 5 (finish)
            val result = fetchQuestionsUseCase.fetchLatestQuestions()
            try {
                when (result) {
                    is FetchQuestionsUseCase.Result.Success -> {
                        viewMvc.bindQuestions(result.questions)
                        isDataLoaded = true
                    }
                    is FetchQuestionsUseCase.Result.Failure -> {
                        onFetchFailed()
                    }
                }
            } finally {
                viewMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}