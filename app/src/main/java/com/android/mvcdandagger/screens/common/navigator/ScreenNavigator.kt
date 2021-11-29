package com.android.mvcdandagger.screens.common.navigator

import android.app.Activity
import android.content.Context
import com.android.mvcdandagger.screens.questiondetails.QuestionDetailsActivity

class ScreenNavigator(
    private val activity: Activity
) {

    //todo 1 (next QuestionsListActivity)
    fun toQuestionDetails(questionId:String){
        QuestionDetailsActivity.start(activity, questionId)
    }

    fun navigateBack(){
        activity.onBackPressed()
    }

}