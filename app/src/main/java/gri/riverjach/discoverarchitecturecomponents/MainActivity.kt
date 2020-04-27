package gri.riverjach.discoverarchitecturecomponents

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_main.*

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
    private val counter = MutableLiveData<Int>()
    private lateinit var speedLiveData: SpeedLiveData
    private val nameLiveData = MutableLiveData<String>()
    private var nameIndex = 0
    private lateinit var lengthLiveData: LiveData<Int>

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

        // Utilisation du LiveData
        counter.value = 0
        incrementButton.setOnClickListener {
            counter.value = counter.value!! + 1
            incrementTextView.setText(counter.value.toString())
        }

        // Version sans lambda
        /*
            val observer : Observer<Int> = object : Observer<Int>{
                override fun onChanged(t: Int?) {
                    Log.i("MainActivity", "New counter value=$t")
                }

            }
        */
        // Version Lambda
        val observer: Observer<Int> = Observer { newValue ->
            Log.i("MainActivity", "New counter value=$newValue")
        }

        counter.observe(this, observer)
        // Fin Utilisation du LiveData //

        // Personalisation LiveData
        val car = Car()
        speedLiveData = SpeedLiveData(car)
        speedLiveData.observe(this, Observer { speed ->
            Log.w("MainActivity", "UPDATE UI with speed=$speed")
        })

        car.startEngine()
        // Fin personalisation LiveData //

        // transformation LiveData
        updateNameButton.setOnClickListener {
            nameLiveData.value = when (nameIndex) {
                0 -> "Bob"
                1 -> "Bobette"
                2 -> "Alice"
                else -> "Who?"
            }
            Log.i("MainActivity", "New name value=${nameLiveData.value}")
            nameIndex = (nameIndex + 1) % 3
        }
        lengthLiveData = Transformations.map(nameLiveData) { name ->
            Log.i("MainActivity", "Transforming string => int")
            name.length
        }
        lengthLiveData.observe(this, Observer { length ->
            nameLengthTextView.setText("New length: $length")
        })
        // Fin transformation LiveData //

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
