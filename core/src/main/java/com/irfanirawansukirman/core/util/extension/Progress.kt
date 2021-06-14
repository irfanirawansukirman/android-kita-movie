package com.irfanirawansukirman.core.util.extension

import androidx.fragment.app.FragmentManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.irfanirawansukirman.core.ui.progress.ProgressDialogFragment

private var progress: ProgressDialogFragment? = null

fun initProgressDialog() {
    if (progress == null) {
        progress = ProgressDialogFragment.newInstance()
        progress?.isCancelable = false
    }
}

fun showProgress(fragmentManager: FragmentManager) {
    if (!progress?.isAdded.orDefault(false) && !progress?.isVisible.orDefault(false)) {
        progress?.show(fragmentManager, progress?.tag)
    }
}

fun hideProgress() {
    if (progress?.isAdded.orDefault(true) && progress?.isVisible.orDefault(true)) {
        progress?.dismiss()
    }
}

fun SwipeRefreshLayout.play() {
    if (!isRefreshing) isRefreshing = true
}

fun SwipeRefreshLayout.stop() {
    if (isRefreshing) isRefreshing = false
}

fun SwipeRefreshLayout.executeWhenStartProgress(block: () -> Unit) {
    setOnRefreshListener { block() }
}