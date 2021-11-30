package com.android.mvcdandagger.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.mvcdandagger.MyApplication

open class BaseActivity:AppCompatActivity() {

    //todo 6 (next QuestionsListActivity)
    val appCompositionRoot get() = (application as MyApplication).appCompositionRoot
}