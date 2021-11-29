package com.android.mvcdandagger.screens.questiondetails

import com.android.mvcdandagger.Constants
import com.android.mvcdandagger.networking.StackoverflowApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchDetailsQuestionsUseCase {


    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var stackoverflowApi: StackoverflowApi = retrofit.create(StackoverflowApi::class.java)


    suspend fun fetchQuestionDetails(questionId:String){
        withContext(Dispatchers.IO){
            try {
                val response = stackoverflowApi.questionDetails(questionId)
                if (response.isSuccessful && response.body() != null){
                    mvc.bindQuestions(response.body()!!.question.body)
                }else{
                    onFetchFailed()
                }
            }catch (t: Throwable){
                if (t !is CancellationException){
                    onFetchFailed()
                }
            }finally {
                mvc.hideProgressIndication()
            }
        }
    }
}