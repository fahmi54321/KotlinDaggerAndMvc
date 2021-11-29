package com.android.mvcdandagger.screens.questiondetails

import com.android.mvcdandagger.Constants
import com.android.mvcdandagger.networking.StackoverflowApi
import com.android.mvcdandagger.questions.Question
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchQuestionsUseCase {

    //todo 4 (next QuestionsListActivity)
    sealed class Result {
        class Success(val questions: List<Question>) : Result()
        object Failure : Result()
    }

    //todo 1
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val stackoverflowApi: StackoverflowApi = retrofit.create(StackoverflowApi::class.java) //todo 2

    //todo 3
    suspend fun fetchLatestQuestions(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = stackoverflowApi.lastActiveQuestions(20)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!.questions)
                } else {
                    return@withContext Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }
}