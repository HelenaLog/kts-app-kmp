package com.github.helenalog.ktsappkmp.presentation.ui.components

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@Composable
actual fun AppSwipeRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val contentState = rememberUpdatedState(content)
    val onRefreshState = rememberUpdatedState(onRefresh)

    AndroidView(
        factory = { context ->
            SwipeRefreshLayout(context).apply {
                addView(
                    ComposeView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setContent { contentState.value() }
                    }
                )
            }
        },
        update = { layout ->
            layout.setOnRefreshListener { onRefreshState.value() }
            layout.isRefreshing = isRefreshing
        }
    )
}