package com.yu.pl.app.challeng.notemark.core.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

sealed class SyncInterval(val name:String){
    data object Manual: SyncInterval("manual")
    data class Minutes15(val interval: Duration = 15.minutes):SyncInterval("15 minutes")
    data class Minutes30(val interval: Duration = 30.minutes):SyncInterval("30 minutes")
    data class Hour1(val interval: Duration = 1.hours):SyncInterval("1 hour")

    companion object {
        fun fromNameOrThrow(name: String): SyncInterval {
            return when (name) {
                Manual.name -> Manual
                Minutes15().name -> Minutes15()
                Minutes30().name -> Minutes30()
                Hour1().name -> Hour1()
                else -> throw IllegalArgumentException("Unknown SyncInterval name: $name")
            }
        }
    }
}


