package com.example.rickandmortyapp.base

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.annotation.MainThread
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setInserts(left=true, right=true)
    }

    protected fun initToolbar(toolbar: Toolbar, navigateUp: Boolean = true) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        toolbar.setInserts(top=true)
        if (navigateUp) {
//            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        }
    }

    protected fun View.setInserts(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false
    ) {
        val oldPaddings = Rect(paddingLeft, paddingTop, paddingRight, paddingBottom
        )
        val isPaddings = Rect()
        isPaddings.left = if (left) 1 else 0
        isPaddings.top = if (top) 1 else 0
        isPaddings.right = if (right) 1 else 0
        isPaddings.bottom = if (bottom) 1 else 0

        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
            val sysInserts = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updatePadding(
                left = oldPaddings.left + sysInserts.left * isPaddings.left,
                right = oldPaddings.right + sysInserts.right * isPaddings.right,
                top = oldPaddings.top + sysInserts.top * isPaddings.top,
                bottom = oldPaddings.bottom + sysInserts.bottom * isPaddings.bottom,
            )

            WindowInsetsCompat.Builder()
                .setInsets(
                    WindowInsetsCompat.Type.systemBars(), Insets.of(
                        sysInserts.left - sysInserts.left * isPaddings.left,
                        sysInserts.top - sysInserts.top * isPaddings.top,
                        sysInserts.right - sysInserts.right * isPaddings.right,
                        sysInserts.bottom - sysInserts.bottom * isPaddings.bottom,
                    )
                )
                .build()
        }
    }

 @MainThread
    inline fun <reified VM : ViewModel> Fragment.viewModels(
        crossinline viewModelProducer: () -> VM
    ): Lazy<VM> {
        return lazy(LazyThreadSafetyMode.NONE) { createViewModel { viewModelProducer() } }
    }

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.createViewModel(
        crossinline viewModelProducer: () -> VM
    ): VM {
        val factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <VM : ViewModel> create(modelClass: Class<VM>) = viewModelProducer() as VM
        }
        val viewModelProvider = ViewModelProvider(this, factory)
        return viewModelProvider[VM::class.java]
    }

}
