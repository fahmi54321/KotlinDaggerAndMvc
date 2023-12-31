package com.android.mvcdandagger.screens.questiondetails

import com.android.mvcdandagger.Constants
import com.android.mvcdandagger.networking.StackoverflowApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchDetailsQuestionsUseCase {

    sealed class ResultDetails{
        class SuccessDetails(val question: String) :ResultDetails()
        object Failure:ResultDetails()
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var stackoverflowApi: StackoverflowApi = retrofit.create(StackoverflowApi::class.java)


    suspend fun fetchQuestionDetails(questionId:String):ResultDetails{
        return withContext(Dispatchers.IO){
            try {
                val response = stackoverflowApi.questionDetails(questionId)
                if (response.isSuccessful && response.body() != null){
                    ResultDetails.SuccessDetails(response.body()!!.question.body)
                }else{
                    ResultDetails.Failure
                }
            }catch (t: Throwable){
                if (t !is CancellationException){
                    ResultDetails.Failure
                }else{
                    throw t
                }
            }
        }
    }
}