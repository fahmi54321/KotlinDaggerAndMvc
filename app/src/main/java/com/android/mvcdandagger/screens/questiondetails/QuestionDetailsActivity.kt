package com.android.mvcdandagger.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.mvcdandagger.Constants
import com.android.mvcdandagger.MyApplication
import com.android.mvcdandagger.R
import com.android.mvcdandagger.networking.StackoverflowApi
import com.android.mvcdandagger.screens.common.activities.BaseActivity
import com.android.mvcdandagger.screens.common.dialogs.DialogsNavigator
import com.android.mvcdandagger.screens.common.dialogs.ServerErrorDialogFragment
import com.android.mvcdandagger.screens.common.navigator.ScreenNavigator
import com.android.mvcdandagger.screens.common.toolbar.MyToolbar
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionDetailsActivity : BaseActivity(),QuestionDetailsListViewMvc.Listeners {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionId: String
    private lateinit var mvc: QuestionDetailsListViewMvc
    private lateinit var dialogsNavigator: DialogsNavigator
    private lateinit var screenNavigator: ScreenNavigator
    private lateinit var fetchDetailQuestionsUseCase: FetchDetailQuestionsUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mvc = QuestionDetailsListViewMvc(LayoutInflater.from(this),null)

        setContentView(mvc.rootView)

        questionId = intent.extras!!.getString(EXTRA_QUESTION_ID)!!
        dialogsNavigator = compositionRoot.dialogNavigator //todo 5 (finish)
        screenNavigator = compositionRoot.screenNavigator
        fetchDetailQuestionsUseCase = compositionRoot.fetchDetailQuestionsUseCase
    }

    override fun onStart() {
        super.onStart()
        mvc.registerListener(this)
        fetchQuestionDetails()
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        mvc.unRegisterListener(this)
    }

    override fun onBackClicked() {
        screenNavigator.navigateBack()
    }


    private fun fetchQuestionDetails(){
        coroutineScope.launch {
            mvc.showProgressIndication()
            try {
                val result = fetchDetailQuestionsUseCase.fetchQuestionDetails(questionId)
                when(result){
                    is FetchDetailQuestionsUseCase.ResultDetails.Success->{
                        mvc.bindQuestions(result.questionId)
                    }
                    is FetchDetailQuestionsUseCase.ResultDetails.Failure->{
                        onFetchFailed()
                    }
                }
            }finally {
                mvc.hideProgressIndication()
            }

        }
    }

    private fun onFetchFailed() {
        dialogsNavigator.showServerErrorDialogFragment()
    }

    companion object {
        const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"
        fun start(context: Context, questionId: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionId)
            context.startActivity(intent)
        }
    }
}