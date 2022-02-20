package com.example.rickandmortyapp.utils

import android.graphics.Rect
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun View.setSystemInserts(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false,
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
