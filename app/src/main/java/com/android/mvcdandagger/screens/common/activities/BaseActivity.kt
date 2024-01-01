package com.android.mvcdandagger.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.mvcdandagger.MyApplication
import com.android.mvcdandagger.common.composition.ActivityCompositionRoot

open class BaseActivity:AppCompatActivity() {

    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val compositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }
}