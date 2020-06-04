package gri.riverjach.discoverarchitecturecomponents

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MyRegistry : LifecycleOwner {
    val lifecycleRegistry = LifecycleRegistry(this)
    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}

val timeLiveData = MutableLiveData<String>()

fun getTimedName(name: String): LiveData<String> {
    timeLiveData.value = "$name ${SystemClock.elapsedRealtime()}"
    return timeLiveData
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
    private lateinit var nameTimeLiveData: LiveData<String>
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private lateinit var connectivityEnumLiveData: LiveData<Connectivity>
    private lateinit var viewModel: UserViewModel
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Debut Worker
        // Tache unique
//        val work = OneTimeWorkRequestBuilder<TestWorker>()
//            .build()

        // Tache périodique (Valeur minimun 15 mininutes)
        val work = PeriodicWorkRequestBuilder<TestWorker>(30, TimeUnit.MINUTES)
            .build()

        // Met le worker dans la queue pour une exécution unique
        WorkManager.getInstance(applicationContext).enqueue(work)

        // Vérification du status du worker
        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(work.id)
            .observe(this, Observer { workStatus ->
                Log.i("MainActivity", "workStatus=$workStatus")

                if (workStatus != null && !workStatus.state.isFinished) {
                    Log.d("MainActivity", "Not yet finished")
                }
            })


        // Fin Worker

        // TP FOLDER
        findViewById<Button>(R.id.start_activity_folder_button).setOnClickListener {
            Log.i("MainActivity", "Start folder activity click")
            val intent = Intent(this, FolderActivity::class.java)
            startActivity(intent)
        }
        //FIN TP FOLDER

        // Utilisation de RoomDatabse
        val userDao = App.database.userDao()

        // faire un thread car on ne peut pas utiliser room
        // dans le thread principal pour faire des opération sur la BDD
        Executors.newSingleThreadExecutor().execute {
            userDao.insertUser(User(0, "Bob", 10, "bob@meil.f"))
            userDao.insertUser(User(0, "Bobette", 19, "bobette@meil.f"))
        }


        userDao.getAllUsers().observe(this, Observer { users ->
            Log.i("MainActivity", "users=$users")
        })

        // Fin Utilisation de RoomDatabse

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
        // SwitchMap LiveData (chainage des LiveData)
        nameTimeLiveData = Transformations.switchMap(nameLiveData) { name ->
            getTimedName(name)
        }
        nameTimeLiveData.observe(this, Observer { nameTime ->
            nameLengthTimeTextView.setText(nameTime)
        })
        // Fin SwitchMap LiveData //
        // TP Surveiller la connectivité avec un LiveData
        connectivityLiveData = ConnectivityLiveData(this)
        connectivityEnumLiveData = Transformations.map(connectivityLiveData) { connected ->
            when (connected) {
                true -> Connectivity.CONNECTED
                else -> Connectivity.DISCONNECTED
            }
        }

        // Version true/false
        connectivityLiveData.observe(this, Observer { connected ->
            Log.i("MainActivity", "Network connected=$connected")
        })
        // Version avec Enum
        connectivityEnumLiveData.observe(this, Observer { connected ->
            Log.i("MainActivity", "Network connected=$connected")
        })
        // Fin TP Surveiller la connectivité avec un LiveData //

        // test ViewModel
        Log.d("MainActivity", "onCreate()")
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // Test Fragment Attention
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, MainFragment())
            .commit()

        // Test ViewModel avec LiveData
        /*
        viewModel.getUser(1).observe(this, Observer { user ->
            Log.i("MainActivity", "UI Received user=$user")
        })
        */

        // Test ViewModel et State
        viewModel.user.observe(this, Observer { state ->
            textView.text = state!!.user?.name
            button.visibility = if (state.buttonVisibility) View.VISIBLE else View.INVISIBLE
            errorMessageTextView.text = state.errorMessage
        })

        viewModel.loadUser(1)

        // Avec le state Sealed
        viewModel.userSealed.observe(this, Observer { updateUI(it!!) })
        viewModel.loadUserSealed(2)

        // Exercice login
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.getState().observe(this, Observer { upadteLoginUI(it!!) })

        loginButton.setOnClickListener {
            val username = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginViewModel.login(username, password)
        }
        // Fin test ViewModel //

    }

    private fun upadteLoginUI(state: LoginViewModelState) {
        return when (state) {
            LoginViewModelStateSucces -> {
                errorMessageLoginTextView.visibility = View.INVISIBLE
                loginButton.isEnabled = state.loginButtonEnable
                Toast.makeText(this, "Logged! Loading next activity...", Toast.LENGTH_SHORT).show()
            }
            is LoginViewModelStateError -> {
                loginButton.isEnabled = state.loginButtonEnable
                errorMessageLoginTextView.visibility = View.VISIBLE
                errorMessageLoginTextView.text = state.errorMessage
            }
        }
    }

    private fun updateUI(state: MyViewModelStateSealed) {
        return when (state) {
            is MyViewModelLoading -> {
                textView.text = "Loading..."
                button.visibility = if (state.buttonVisibility) View.VISIBLE else View.INVISIBLE
                errorMessageTextView.text = state.errorMessage
            }
            is MyViewModelSucces -> {
                textView.text = state!!.user?.name
                button.visibility = if (state.buttonVisibility) View.VISIBLE else View.INVISIBLE
                errorMessageTextView.visibility = View.INVISIBLE
            }
            is MyViewModelError -> {
                button.visibility = if (state.buttonVisibility) View.VISIBLE else View.INVISIBLE
                errorMessageTextView.visibility = View.VISIBLE
                errorMessageTextView.text = state.errorMessage
            }
        }
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

        Log.d("MainActivity", "onDestroy()")
    }
}

enum class Connectivity {
    DISCONNECTED,
    CONNECTED
}
