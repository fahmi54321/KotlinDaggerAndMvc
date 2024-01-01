package com.android.mvcdandagger.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.mvcdandagger.MyApplication
import com.android.mvcdandagger.common.composition.ActivityCompositionRoot

open class BaseActivity:AppCompatActivity() {

    //todo 4
    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    //todo 3
    val compositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot) // todo 5 (next ActivityCompositionRoot)
    }
}