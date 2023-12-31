package com.android.mvcdandagger.screens.questiondetails

import com.android.mvcdandagger.Constants
import com.android.mvcdandagger.networking.StackoverflowApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchDetailQuestionsUseCase(retrofit: Retrofit) {

    sealed class ResultDetails{
        class Success(val questionId: String) :ResultDetails()
        object Failure:ResultDetails()
    }

    private var stackoverflowApi: StackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    suspend fun fetchQuestionDetails(detailsId:String):ResultDetails{
        return withContext(Dispatchers.IO){
            try {
                val response = stackoverflowApi.questionDetails(detailsId)
                if (response.isSuccessful && response.body() != null){
                    return@withContext ResultDetails.Success(response.body()!!.question.body)
                }else{
                    return@withContext ResultDetails.Failure
                }
            }catch (t: Throwable){
                if (t !is CancellationException){
                    return@withContext ResultDetails.Failure
                }else{
                    throw t
                }
            }
        }
    }

}