package gri.riverjach.discoverarchitecturecomponents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val user = MutableLiveData<User>()

    init {
        Log.i("UserViewModel", "init()")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("UserViewModel", "onCleared()")
    }

    fun getUser(userId: Int): LiveData<User> {
        loadUser(userId)
        return user
    }

    private fun loadUser(userId: Int) {
        user.value = User(userId, "Bob $userId", userId + 20)
    }
}