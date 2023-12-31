package com.android.mvcdandagger.screens.questionslist

import com.android.mvcdandagger.Constants
import com.android.mvcdandagger.networking.StackoverflowApi
import com.android.mvcdandagger.questions.Question
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchQuestionsUseCase(
    private val stackoverflowApi: StackoverflowApi //todo 1 (next MyApplication)
) {

    sealed class Result {
        class Success(val questions: List<Question>) : Result()
        object Failure : Result()
    }

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