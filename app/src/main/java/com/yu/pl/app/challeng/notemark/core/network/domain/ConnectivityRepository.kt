package com.yu.pl.app.challeng.notemark.core.network.domain

import kotlinx.coroutines.flow.StateFlow

interface ConnectivityRepository{
    val internetConnection: StateFlow<Boolean>
}