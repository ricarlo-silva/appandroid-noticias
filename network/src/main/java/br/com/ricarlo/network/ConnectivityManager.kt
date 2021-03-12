package br.com.ricarlo.network

// import android.content.Context
// import android.net.ConnectivityManager
// import android.net.Network
// import android.net.NetworkCapabilities
// import android.os.Build
// import kotlinx.coroutines.channels.BroadcastChannel
// import kotlinx.coroutines.channels.Channel

// class ConnectivityManager(private val context: Context) {
//
//    private val channel = BroadcastChannel<Boolean>(Channel.BUFFERED)
//
//    fun isConnected() : Boolean {
//
//        val connectivityManager = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)
//
//        val d = object : ConnectivityManager.NetworkCallback() {
//            override fun onAvailable(network: Network) {
//                super.onAvailable(network)
//
//            }
//
//            override fun onLost(network: Network) {
//                super.onLost(network)
//            }
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            connectivityManager?.registerDefaultNetworkCallback(d)
//        }
//
//        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//             connectivityManager?.activeNetworkInfo?.isConnected ?: false
//        } else {
//            connectivityManager?.allNetworks?.any {
//                val networkCapabilities = connectivityManager.getNetworkCapabilities(it)
//                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
//            } ?: false
//        }
//    }
//
// }
