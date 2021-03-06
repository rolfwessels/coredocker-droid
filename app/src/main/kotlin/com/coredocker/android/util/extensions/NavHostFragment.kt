package com.coredocker.android.util.extensions

import androidx.navigation.fragment.NavHostFragment

fun NavHostFragment.setNavigationGraph(graph: Int) {
    navController.graph = navController.navInflater.inflate(graph)
}