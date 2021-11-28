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

class QuestionsListActivity : AppCompatActivity(), QuestionsListViewMvc.Listener{ //todo 11

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var stackoverflowApi: StackoverflowApi

    private var isDataLoaded = false

    //todo 12
    private lateinit var viewMvc: QuestionsListViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //todo 13
        viewMvc = QuestionsListViewMvc(LayoutInflater.from(this),null)

        setContentView(viewMvc.rootView) //todo 26


        //init retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        stackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this) //todo 14
        if (!isDataLoaded){
            fetchQuestions()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        viewMvc.unRegisterListener(this) //todo 15
    }

    //todo 16 (next QuestionsListViewMvc)
    override fun onRefreshClicked() {
        fetchQuestions()
    }

    //todo 19 (next QuestionsListViewMvc)
    override fun onQuestionClicked(clickQuestion: Question) {
        QuestionDetailsActivity.start(this, clickQuestion.id)
    }

    private fun fetchQuestions(){
        coroutineScope.launch {
            viewMvc.showProgressIndication() //todo 24
            try {
                val response = stackoverflowApi.lastActiveQuestions(20)
                if (response.isSuccessful && response.body() != null){
                    viewMvc.bindQuestions(response.body()!!.questions) //todo 27 (next QuestionsListViewMvc)
                    isDataLoaded = true
                }else{
                    onFetchFailed()
                }
            }catch (t:Throwable){
                onFetchFailed()
            }finally {
                viewMvc.hideProgressIndication() //todo 25
            }
        }
    }

    private fun onFetchFailed(){
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(),null)
            .commitAllowingStateLoss()
    }

}