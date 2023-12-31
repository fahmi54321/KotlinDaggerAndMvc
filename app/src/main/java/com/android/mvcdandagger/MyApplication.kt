package com.android.mvcdandagger

import android.app.Application
import com.android.mvcdandagger.common.composition.AppCompositionRoot
import com.android.mvcdandagger.networking.StackoverflowApi
import com.android.mvcdandagger.screens.questiondetails.FetchDetailQuestionsUseCase
import com.android.mvcdandagger.screens.questionslist.FetchQuestionsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {

    //todo 2
    lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot() //todo 3 (next QuestionsListActivity)
        super.onCreate()
    }

}