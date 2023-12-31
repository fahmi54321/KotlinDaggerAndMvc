package com.android.mvcdandagger.screens.common.dialogs

import androidx.fragment.app.FragmentManager

class DialogsNavigator(
    private val fragmentManager: FragmentManager
) {

    fun showServerErrorDialogFragment(){
        fragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}