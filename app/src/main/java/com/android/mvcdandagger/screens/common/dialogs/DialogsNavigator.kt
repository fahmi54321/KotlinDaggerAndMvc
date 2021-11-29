package com.android.mvcdandagger.screens.common.dialogs

import androidx.fragment.app.FragmentManager

//todo 1
class DialogsNavigator(
    private val fragmentManager: FragmentManager
) {

    //todo 2 (next QuestionsListActivity)
    fun showServerErrorDialogFragment(){
        fragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}