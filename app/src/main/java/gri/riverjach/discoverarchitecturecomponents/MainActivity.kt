package gri.riverjach.discoverarchitecturecomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class MyRegistry : LifecycleOwner {
    val lifecycleRegistry = LifecycleRegistry(this)
    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var videoPlayer: VideoPlayer
    private lateinit var videoPlayerCompoment: VideoPlayerCompoment
    private lateinit var myLocationListener: MyLocationListener
    private val myRegistry = MyRegistry()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Exemple sans lifecycle
        videoPlayer = VideoPlayer()

        // Modifier l'état de notre registry
        myRegistry.lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED)
        // Il faut ajouter un abonement à l'observer pour le videoPlayerCompoment
        videoPlayerCompoment = VideoPlayerCompoment(myRegistry.lifecycle)
        myRegistry.lifecycle.addObserver(videoPlayerCompoment)

        myLocationListener = MyLocationListener(lifecycle)
        lifecycle.addObserver(myLocationListener)
    }

    // Ajout de la surcharge que pour l'exemple du videoPlayer sans lifeCycle
    // Utile pour gérer son propre regsitry
    override fun onResume() {
        super.onResume()
        videoPlayer.start()
        // Change l'état du resgitry à Resumed et envoie le nouvel Event
        myRegistry.lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED)
    }

    // Ajout de la surcharge que pour l'exemple du videoPlayer sans lifeCycle
    override fun onPause() {
        super.onPause()
        videoPlayer.stop()
    }

    // Utile pour gérer son propre regsitry
    override fun onDestroy() {
        super.onDestroy()
        // Change l'état du resgitry à Distroyed et envoie le nouvel Event
        myRegistry.lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED)
    }
}
