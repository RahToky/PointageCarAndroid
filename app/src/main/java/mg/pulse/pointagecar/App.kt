package mg.pulse.pointagecar

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class App: Application() {

    companion object Factory{
        private var singleton: App? = null
        fun getInstance(): App? {
            return singleton
        }
    }

    override fun onCreate() {
        super.onCreate()
        singleton = this
    }
}