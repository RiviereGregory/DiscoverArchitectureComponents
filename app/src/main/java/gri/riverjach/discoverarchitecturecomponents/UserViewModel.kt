package gri.riverjach.discoverarchitecturecomponents

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModelState(
    val user: User?,
    val errorMessage: String = "",
    val buttonVisibility: Boolean = true
)

sealed class MyViewModelStateSealed(
    val user: User? = null,
    val errorMessage: String = "",
    val buttonVisibility: Boolean = true
)

class MyViewModelLoading : MyViewModelStateSealed(buttonVisibility = false)
class MyViewModelSucces(user: User) : MyViewModelStateSealed(user)
class MyViewModelError(errorMessage: String) : MyViewModelStateSealed(
    errorMessage = errorMessage,
    buttonVisibility = false
)

class UserViewModel : ViewModel() {

    val user = MutableLiveData<MyViewModelState>()
    val userSealed = MutableLiveData<MyViewModelStateSealed>()

    fun loadUser(userId: Int) {
        user.value = MyViewModelState(fakeUser(userId), "", true)
    }

    fun loadUserSealed(userId: Int) {
        val handler = Handler()
        handler.post { userSealed.value = MyViewModelLoading() }
        handler.postDelayed({ userSealed.value = MyViewModelSucces(fakeUser(userId)) }, 2000)
        handler.postDelayed({ userSealed.value = MyViewModelError("Could not load user") }, 4000)
    }

    private fun fakeUser(userId: Int): User = User(userId, "Bob $userId", userId + 20)

    /*
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
    */
}