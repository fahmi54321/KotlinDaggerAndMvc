package com.android.mvcdandagger.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.mvcdandagger.MyApplication
import com.android.mvcdandagger.common.composition.ActivityCompositionRoot
import com.android.mvcdandagger.common.composition.PresentationCompositionRoot

open class BaseActivity:AppCompatActivity() {

    //todo 3 (next BaseFragment)
    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot
    val activityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }

    protected val compositionRoot by lazy {
        PresentationCompositionRoot(activityCompositionRoot)
    }
}