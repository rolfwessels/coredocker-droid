package com.coredocker.android.data.network.graphql

import java.math.BigInteger

class PagedFragment<T>(
    val items: List<T>,
    val count: BigInteger?
)
