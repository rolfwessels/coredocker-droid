package com.coredocker.android.util.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun runOnBackground(function: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        function()
    }
}