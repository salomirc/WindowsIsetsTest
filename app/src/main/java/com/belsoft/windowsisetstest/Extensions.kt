package com.belsoft.windowsisetstest

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


const val TAG = "KeyboardVisibility"
var previousImeVisibility: Boolean = false

fun View.showKeyboard() {
    ViewCompat.getWindowInsetsController(this)?.show(WindowInsetsCompat.Type.ime())
}

fun View.hideKeyboard() {
    ViewCompat.getWindowInsetsController(this)?.hide(WindowInsetsCompat.Type.ime())
}

fun View.isKeyboardVisible(keyboardCallback: (visible: Boolean?, imeHeight: Int?) -> Unit) {
    val insets: WindowInsetsCompat? = ViewCompat.getRootWindowInsets(this)
    val imeVisible = insets?.isVisible(WindowInsetsCompat.Type.ime())
    val imeHeight = insets?.getInsets(WindowInsetsCompat.Type.ime())?.bottom
    keyboardCallback(imeVisible, imeHeight)
}

fun ViewGroup.setKeyboardVisibilityListener(keyboardCallback: (visible: Boolean, imeHeight: Int) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { rootView, windowInsets ->
        val imeVisible: Boolean = windowInsets.isVisible(WindowInsetsCompat.Type.ime())
        val imeHeight: Int = windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom

        if (imeVisible != previousImeVisibility) {
            previousImeVisibility = imeVisible
            keyboardCallback(imeVisible, imeHeight)
        }

        val insets = windowInsets.getInsets(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
        )
        rootView.setPadding(insets.left, insets.top, insets.right, insets.bottom)

//        rootView.updateLayoutParams<ViewGroup.MarginLayoutParams>{
//            leftMargin = insets.left
//            topMargin = insets.top
//            bottomMargin = insets.bottom
//            rightMargin = insets.right
//        }

        // We return the new WindowInsets.CONSUMED if we want to stop the insets being
        // dispatched any further into the rootView hierarchy.
        // or
        // you can simply return windowInsets
        windowInsets
    }
}