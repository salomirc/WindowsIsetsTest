package com.belsoft.windowsisetstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.belsoft.windowsisetstest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        //need it by setOnApplyWindowInsetsListener
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        initializeUI()
    }

    private fun initializeUI() {

        binding.topButton.setOnClickListener {
            viewModel.toggleKeyboard()
        }

        binding.bottomButton.setOnClickListener {
            it.isKeyboardVisible { visible, imeHeight ->
                logKeyboardInfo(visible, imeHeight)
            }
        }

        viewModel.keyboardToggle.observe(this) {
            if (it) binding.root.showKeyboard() else binding.root.hideKeyboard()
        }

        binding.root.setKeyboardVisibilityListener { isVisible, height ->
            logKeyboardInfo(isVisible, height)
        }

        binding.coordinatorLayoutContainer.setWindowInsetsAnimationCallBack(
            onPrepare = {
                Log.d(TAG, "InsetAnimation onPrepare()")
            },
            onStart = {
                Log.d(TAG, "InsetAnimation onStart()")
            },
            onProgress = { insets: WindowInsetsCompat,
                           runningAnimations: MutableList<WindowInsetsAnimationCompat> ->
                Log.d(TAG, "InsetAnimation onProgress()")
                return@setWindowInsetsAnimationCallBack  insets
            },
            onEnd = {
                Log.d(TAG, "InsetAnimation onEnd()")
            }
        )
    }

    private fun logKeyboardInfo(isVisible: Boolean?, height: Int?) {
        Log.d(TAG, "Keyboard is visibile : $isVisible")
        Log.d(TAG, "Keyboard height : $height px")
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}