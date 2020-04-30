package gri.riverjach.discoverarchitecturecomponents

import android.util.Log
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    init {
        Log.i("UserViewModel", "init()")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("UserViewModel", "onCleared()")
    }
}