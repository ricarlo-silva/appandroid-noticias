package br.com.ricarlo.common.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    protected lateinit var binding: T

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(savedInstanceState = savedInstanceState)
    }

    protected fun handlerError(throwable: Throwable) {
        (activity as? BaseActivity<*>)?.handlerError(throwable)
    }

    open fun showLoading(message: String?) {
        (activity as? BaseActivity<*>)?.showLoading(message)
    }

    open fun hideLoading() {
        (activity as? BaseActivity<*>)?.hideLoading()
    }

    fun openActivity(classOpen: Class<*>, bundle: Bundle? = null) {
        (activity as? BaseActivity<*>)?.openActivity(classOpen, bundle)
    }
}