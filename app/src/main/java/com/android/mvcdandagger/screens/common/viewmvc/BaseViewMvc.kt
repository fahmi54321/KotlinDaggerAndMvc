package com.android.mvcdandagger.screens.common.viewmvc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.android.mvcdandagger.R

open class BaseViewMvc<LISTENER_TYPE>(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?,
    @LayoutRes private val layoutId:Int //todo (base view untuk findViewById)
) {

    //todo (base view untuk listener)
    protected val listeners = HashSet<LISTENER_TYPE>()

    //todo (base view untuk findViewById)
    val rootView: View = layoutInflater.inflate(layoutId, parent, false)

    //todo (base view untuk context)
    protected val context: Context get() = rootView.context

    //todo (base view untuk listener)
    fun registerListener(listener: LISTENER_TYPE) {
        listeners.add(listener)
    }

    //todo (base view untuk listener)
    fun unRegisterListener(listener: LISTENER_TYPE) {
        listeners.remove(listener)
    }

    //todo (base view untuk findViewById)
    protected fun <T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }
}