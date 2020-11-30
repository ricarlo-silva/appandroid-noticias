package br.com.ricarlo.common.util.resources

import androidx.annotation.StringRes

interface IResourcesManager {
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String
}