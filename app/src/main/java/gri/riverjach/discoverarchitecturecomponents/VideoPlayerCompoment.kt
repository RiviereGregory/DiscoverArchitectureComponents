package gri.riverjach.discoverarchitecturecomponents

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class VideoPlayerCompoment(private val lifecycle: Lifecycle) : LifecycleObserver {

    // Permet de réagir sur un event de type ON_RESUME pour éviter de surcharge dans le mainActivity onResume()
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun start() {
        Log.i("VideoPlayerCompoment", "Starting playback")

        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)){
            Log.i("VideoPlayerCompoment", "Initialize video buffering")
        }
    }

    // Permet de réagir sur un event de type ON_PAUSE pour éviter de surcharge dans le mainActivity onPause()
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stop() {
        Log.i("VideoPlayerCompoment", "Stopping playback")
    }

}