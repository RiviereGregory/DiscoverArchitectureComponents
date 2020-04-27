package gri.riverjach.discoverarchitecturecomponents

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyLocationListener(private val lifecycle: Lifecycle) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            Log.i("MyLocationListener", "Starting")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Log.i("MyLocationListener", "Stopping")
    }
}