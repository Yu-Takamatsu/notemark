package com.yu.pl.app.challeng.notemark.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {
    @Serializable
    data object Landing: Destination
    @Serializable
    data object Registration: Destination
    @Serializable
    data object Login: Destination
    @Serializable
    data object Dummy: Destination
}