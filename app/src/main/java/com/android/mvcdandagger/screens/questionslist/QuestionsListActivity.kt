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

class QuestionsListActivity : AppCompatActivity(), QuestionsListViewMvc.Listener{

    // todo 6 (deklarasi variabel)
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var isDataLoaded = false
    private lateinit var stackoverflowApi: StackoverflowApi
    private lateinit var viewMvc: QuestionsListViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // todo 7 (init)
        viewMvc = QuestionsListViewMvc(LayoutInflater.from(this),null)
        setContentView(viewMvc.rootView)
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        stackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    }

    // todo 8 (implementasi method / fungsi)

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        if (!isDataLoaded){
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

    private fun fetchQuestions(){
        coroutineScope.launch {
            viewMvc.showProgressIndication()
            try {
                val response = stackoverflowApi.lastActiveQuestions(20)
                if (response.isSuccessful && response.body() != null){
                    viewMvc.bindQuestions(response.body()!!.questions)
                    isDataLoaded = true
                }else{
                    onFetchFailed()
                }
            }catch (t:Throwable){
                onFetchFailed()
            }finally {
                viewMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed(){
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(),null)
            .commitAllowingStateLoss()
    }

}