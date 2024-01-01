package com.android.mvcdandagger.common.composition

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.android.mvcdandagger.screens.common.dialogs.DialogsNavigator
import com.android.mvcdandagger.screens.common.navigator.ScreenNavigator
import com.android.mvcdandagger.screens.questiondetails.FetchDetailQuestionsUseCase
import com.android.mvcdandagger.screens.questionslist.FetchQuestionsUseCase

class ActivityCompositionRoot(
    private val activity: AppCompatActivity, //todo 1
    private val appCompositionRoot: AppCompositionRoot
) {

    val screenNavigator by lazy {
        ScreenNavigator(activity)
    }

    private val fragmentManager get() = activity.supportFragmentManager  //todo 2
    val dialogNavigator get() = DialogsNavigator(fragmentManager)  //todo 3 (next QuestionsListActivity)

    private val stackoverflowApi get() = appCompositionRoot.stackoverflowApi

    val fetchQuestionsUseCase get() = FetchQuestionsUseCase(stackoverflowApi)
    val fetchDetailQuestionsUseCase get() = FetchDetailQuestionsUseCase(stackoverflowApi)
}