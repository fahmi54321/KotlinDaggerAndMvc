package com.android.mvcdandagger.common.composition

import androidx.annotation.UiThread
import com.android.mvcdandagger.Constants
import com.android.mvcdandagger.networking.StackoverflowApi
import com.android.mvcdandagger.screens.questiondetails.FetchDetailQuestionsUseCase
import com.android.mvcdandagger.screens.questionslist.FetchQuestionsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//todo 1

@UiThread
class AppCompositionRoot {

    //todo 2
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //todo 3 (finish)
    private val stackoverflowApi : StackoverflowApi by lazy {
        retrofit.create(StackoverflowApi::class.java)
    }
    val fetchQuestionsUseCase get() = FetchQuestionsUseCase(stackoverflowApi)
    val fetchDetailQuestionsUseCase get() = FetchDetailQuestionsUseCase(stackoverflowApi)
}