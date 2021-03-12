package br.com.ricarlo.common.util.resources

import android.content.Context

class ResourcesManagerImpl(
    private val context: Context
) : IResourcesManager {

    override fun getString(resId: Int, vararg formatArgs: Any?): String {
        return context.getString(resId, formatArgs)
    }
}
