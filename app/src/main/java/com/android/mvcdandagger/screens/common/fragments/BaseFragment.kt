package com.android.mvcdandagger.screens.common.fragments

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.mvcdandagger.MyApplication
import com.android.mvcdandagger.common.composition.ActivityCompositionRoot
import com.android.mvcdandagger.screens.common.activities.BaseActivity

open class BaseFragment:Fragment() {

    val compositionRoot get() = (requireActivity() as BaseActivity).compositionRoot
}