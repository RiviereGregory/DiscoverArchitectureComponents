package gri.riverjach.discoverarchitecturecomponents

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData

class SpeedLiveData(private val car: Car) : LiveData<Int>(), Car.SpeedListener {

    // abonement au Listener changement de vitesse de car
    override fun onActive() {
        Log.i("SpeedLiveData", "onActive")
        car.speedListener = this
    }

    override fun onInactive() {
        Log.i("SpeedLiveData", "onInactive")
        car.speedListener = null
    }

    override fun onSpeedChanged(speed: Int) {
        value = speed
    }
}

class Car {
    interface SpeedListener {
        fun onSpeedChanged(speed: Int)
    }

    var speedListener: SpeedListener? = null

    fun startEngine() {
        val handler = Handler()
        handler.postDelayed({ notifyspeed(10) }, 1000)
        handler.postDelayed({ notifyspeed(50) }, 5000)
        handler.postDelayed({ notifyspeed(100) }, 7000)
        handler.postDelayed({ notifyspeed(2) }, 7000)
    }

    private fun notifyspeed(speed: Int) {
        Log.i("Car", "New speed=$speed")
        speedListener?.onSpeedChanged(speed)
    }
}