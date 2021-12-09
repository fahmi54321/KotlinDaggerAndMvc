package com.android.mvcdandagger.screens.common.viewmvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.mvcdandagger.screens.questiondetails.QuestionDetailsListViewMvc
import com.android.mvcdandagger.screens.questionslist.QuestionsListViewMvc

class ViewMvcFactory(
    private val layoutInflater: LayoutInflater
) {

    //todo 4 (next ActivityCompositionRoot)
    fun newQuestionsListViewMvc(parent: ViewGroup?): QuestionsListViewMvc{
        return QuestionsListViewMvc(layoutInflater, parent)
    }

    fun newQuestionsDetailsViewMvc(parent: ViewGroup?): QuestionDetailsListViewMvc{
        return QuestionDetailsListViewMvc(layoutInflater,parent)
    }

}