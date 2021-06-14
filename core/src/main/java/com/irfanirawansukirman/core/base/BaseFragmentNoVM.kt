package com.irfanirawansukirman.core.base

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.irfanirawansukirman.core.util.viewmodel.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragmentNoVM<VB : ViewBinding>(
    private val viewBinder: (LayoutInflater) -> ViewBinding
) : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewBinding by lazy { viewBinder.invoke(LayoutInflater.from(requireContext())) as VB }
    val parentActivity by lazy { (requireActivity() as BaseActivity) }
    val fragmentContext by lazy { requireContext() }

    private var isViewCreated = false
    private var isComponentCreated = false

    /** Fragment Is the current state visible  */
    protected var isUIVisible = false

    abstract fun initInjector()
    abstract fun initComponent()
    abstract fun initViewListener()
    abstract fun onViewReady(savedInstanceState: Bundle?, view: View)
    abstract fun onClear()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isUIVisible = true
            onVisible()
        } else {
            isUIVisible = false
            onInvisible()
        }
    }

    //  So
    protected open fun onVisible() {
        lazyLoad()
    }

    // Invisible
    protected open fun onInvisible() {}

    //Delayed loading
    //Subclasses must override this method
    protected abstract fun lazyLoad()

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        if (!isComponentCreated) {
            initComponent()
            isComponentCreated = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isViewCreated) {
            onViewReady(savedInstanceState, view)
            initViewListener()
            isViewCreated = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onClear()
    }

    fun getApplication(): Application = parentActivity.application
}