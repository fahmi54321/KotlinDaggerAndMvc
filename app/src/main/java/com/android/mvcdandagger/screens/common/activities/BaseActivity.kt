package com.android.mvcdandagger.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.mvcdandagger.MyApplication

open class BaseActivity:AppCompatActivity() {

    val appCompositionRoot get() = (application as MyApplication).appCompositionRoot
}