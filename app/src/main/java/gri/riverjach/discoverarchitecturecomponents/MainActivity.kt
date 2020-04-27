package gri.riverjach.discoverarchitecturecomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var videoPlayer: VideoPlayer
    private lateinit var videoPlayerCompoment: VideoPlayerCompoment
    private lateinit var myLocationListener: MyLocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Exemple sans lifecycle
        videoPlayer = VideoPlayer()

        // Il faut ajouter un abonement à l'observer pour le videoPlayerCompoment
        videoPlayerCompoment = VideoPlayerCompoment(lifecycle)
        lifecycle.addObserver(videoPlayerCompoment)

        myLocationListener = MyLocationListener(lifecycle)
        lifecycle.addObserver(myLocationListener)
    }

    // Ajout de la surcharge que pour l'exemple du videoPlayer sans lifeCycle
    override fun onResume() {
        super.onResume()
        videoPlayer.start()
    }

    // Ajout de la surcharge que pour l'exemple du videoPlayer sans lifeCycle
    override fun onPause() {
        super.onPause()
        videoPlayer.stop()
    }
}
