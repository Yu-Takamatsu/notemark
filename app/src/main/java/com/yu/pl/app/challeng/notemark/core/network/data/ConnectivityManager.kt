package com.yu.pl.app.challeng.notemark.core.network.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.yu.pl.app.challeng.notemark.core.network.domain.ConnectivityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ConnectivityManager(
    context: Context,
    private val scope: CoroutineScope,
) : ConnectivityRepository {

    private val _internetConnection = MutableStateFlow(false)

    private var subscriberCount = 0

    override val internetConnection: StateFlow<Boolean> = _internetConnection.onSubscription {
        if (subscriberCount == 0) observeConnectivity()
        subscriberCount++
    }
        .onCompletion {
            subscriberCount--
            if (subscriberCount == 0) unregisterConnectivity()

        }.stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _internetConnection.value
        )

    private val connectivityMgr =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : NetworkCallback() {

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities,
        ) {
            _internetConnection.update {
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
        }

        override fun onAvailable(network: Network) {
            println("onAvailable:${network}")
        }

        override fun onLost(network: Network) {
            _internetConnection.update {
                false
            }
        }
    }

    private fun observeConnectivity() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityMgr.requestNetwork(networkRequest, networkCallback)

    }

    private fun unregisterConnectivity() {
        connectivityMgr.unregisterNetworkCallback(networkCallback)
    }
}