package com.android.mvcdandagger

import android.app.Application
import com.android.mvcdandagger.networking.StackoverflowApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val stackoverflowApi = retrofit.create(StackoverflowApi::class.java) //todo 2 (next QuestionsListActivity)

    override fun onCreate() {
        super.onCreate()
    }

}