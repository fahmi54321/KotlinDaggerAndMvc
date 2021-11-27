package com.android.mvcdandagger.networking

import com.android.mvcdandagger.questions.Question
import com.google.gson.annotations.SerializedName

class QuestionsListResponseSchema(@SerializedName("items") val questions: List<Question>)