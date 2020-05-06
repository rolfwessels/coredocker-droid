package com.coredocker.android.util.extensions

import com.google.gson.Gson

fun Any.dump(description: String? = null) {
    println("-------------------- $description ---------------------\\")
    println(Gson().toJson(this).toString())
    println("-------------------- $description ---------------------/")
}