package com.android.mvcdandagger.common.composition

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.android.mvcdandagger.screens.common.dialogs.DialogsNavigator
import com.android.mvcdandagger.screens.common.navigator.ScreenNavigator
import com.android.mvcdandagger.screens.common.viewmvc.ViewMvcFactory
import com.android.mvcdandagger.screens.questiondetails.FetchDetailQuestionsUseCase
import com.android.mvcdandagger.screens.questionslist.FetchQuestionsUseCase

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
) {

    //todo 2 (next BaseActivity)
    val screenNavigator by lazy {
        ScreenNavigator(activity)
    }

    val layoutInflater get() = LayoutInflater.from(activity)
    val fragmentManager get() = activity.supportFragmentManager
    val stackoverflowApi get() = appCompositionRoot.stackoverflowApi
}