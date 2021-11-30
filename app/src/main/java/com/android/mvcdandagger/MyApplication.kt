package com.android.mvcdandagger

import android.app.Application
import com.android.mvcdandagger.networking.StackoverflowApi
import com.android.mvcdandagger.screens.questiondetails.FetchDetailQuestionsUseCase
import com.android.mvcdandagger.screens.questionslist.FetchQuestionsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val stackoverflowApi = retrofit.create(StackoverflowApi::class.java)
    val fetchQuestionsUseCase get() = FetchQuestionsUseCase(stackoverflowApi) //todo 1 (next QuestionsListActivity)
    val fetchDetailQuestionsUseCase get() = FetchDetailQuestionsUseCase(stackoverflowApi)

    override fun onCreate() {
        super.onCreate()
    }

}