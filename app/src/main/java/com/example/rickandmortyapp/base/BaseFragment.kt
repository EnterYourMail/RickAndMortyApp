package com.example.rickandmortyapp.base

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

open class BaseFragment : Fragment() {

    protected fun initToolbar(toolbar: Toolbar, navigateUp: Boolean = true) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
//        if (navigateUp) {
//            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
//            toolbar.setNavigationOnClickListener { navController.navigateUp() }
//        }

//            ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
//                val sysInserts = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//                view.updatePadding(top = sysInserts.top)
//                insets
//            }
    }

}