package com.example.rickandmortyapp.base

import android.os.Bundle
import android.view.View
import androidx.annotation.MainThread
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmortyapp.utils.setSystemInserts

open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setSystemInserts(left = true, right = true)
    }

    protected fun initToolbar(toolbar: Toolbar) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        toolbar.setSystemInserts(top = true)
    }

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.viewModels(
        crossinline viewModelProducer: () -> VM,
    ): Lazy<VM> {
        return lazy(LazyThreadSafetyMode.NONE) { createViewModel { viewModelProducer() } }
    }

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.createViewModel(
        crossinline viewModelProducer: () -> VM,
    ): VM {
        val factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <VM : ViewModel> create(modelClass: Class<VM>) = viewModelProducer() as VM
        }
        val viewModelProvider = ViewModelProvider(this, factory)
        return viewModelProvider[VM::class.java]
    }

}
