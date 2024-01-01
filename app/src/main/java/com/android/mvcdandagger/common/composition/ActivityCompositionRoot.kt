package com.android.mvcdandagger.common.composition

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.android.mvcdandagger.screens.common.navigator.ScreenNavigator

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
) {

    val screenNavigator by lazy {
        ScreenNavigator(activity)
    }

    val application get() = appCompositionRoot.application

    val layoutInflater: LayoutInflater get() = LayoutInflater.from(activity)
    val fragmentManager get() = activity.supportFragmentManager
    val stackoverflowApi get() = appCompositionRoot.stackoverflowApi
}