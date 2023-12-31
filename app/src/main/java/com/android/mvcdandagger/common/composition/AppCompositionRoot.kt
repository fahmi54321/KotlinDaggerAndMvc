package com.android.mvcdandagger.common.composition

import com.android.mvcdandagger.Constants
import com.android.mvcdandagger.networking.StackoverflowApi
import com.android.mvcdandagger.screens.questiondetails.FetchDetailQuestionsUseCase
import com.android.mvcdandagger.screens.questionslist.FetchQuestionsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppCompositionRoot {

    //todo 1 (next MyApplication)
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val stackoverflowApi = retrofit.create(StackoverflowApi::class.java)
    val fetchQuestionsUseCase get() = FetchQuestionsUseCase(stackoverflowApi)
    val fetchDetailQuestionsUseCase get() = FetchDetailQuestionsUseCase(stackoverflowApi)
}