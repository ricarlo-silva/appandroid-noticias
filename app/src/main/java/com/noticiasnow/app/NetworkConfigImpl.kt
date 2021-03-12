package com.noticiasnow.app

import br.com.ricarlo.network.INetworkConfig
import com.noticiasnow.BuildConfig

class NetworkConfigImpl : INetworkConfig {
    override fun baseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun connectTimeout(): Long {
        return 40
    }

    override fun readTimeout(): Long {
        return 60
    }

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}
