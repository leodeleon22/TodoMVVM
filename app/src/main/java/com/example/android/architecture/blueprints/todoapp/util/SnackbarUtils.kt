package com.example.android.architecture.blueprints.todoapp.util

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Provides a method to show a Snackbar.
 */

    fun View.snack(snackbarText: String) {
        Snackbar.make(this, snackbarText, Snackbar.LENGTH_LONG).show()
    }
