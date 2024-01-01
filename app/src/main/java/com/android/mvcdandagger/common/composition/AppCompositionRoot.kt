package com.android.mvcdandagger.common.composition

import android.app.Application
import androidx.annotation.UiThread
import com.android.mvcdandagger.Constants
import com.android.mvcdandagger.MyApplication
import com.android.mvcdandagger.networking.StackoverflowApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@UiThread
class AppCompositionRoot(val application: Application) { //todo 1 (next ActivityCompositionRoot)

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val stackoverflowApi : StackoverflowApi by lazy {
        retrofit.create(StackoverflowApi::class.java)
    }

}