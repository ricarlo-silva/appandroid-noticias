package br.com.ricarlo.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class ConnectionViewModel(private val context: Context) : LifecycleObserver {

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private val channel = Channel<Boolean>(Channel.BUFFERED)

    private val connectivityManager = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)


    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            coroutineScope.launch {
                channel.send(true)
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            coroutineScope.launch {
                channel.send(false)
            }
        }
    }

    private  fun isN() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onStart() {
        if (isN()) {
            connectivityManager?.registerDefaultNetworkCallback(networkCallback)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (isN()) {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        job.cancel()
    }

}