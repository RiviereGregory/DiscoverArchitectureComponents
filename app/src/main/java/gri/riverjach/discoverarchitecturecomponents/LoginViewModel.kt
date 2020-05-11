package gri.riverjach.discoverarchitecturecomponents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class LoginViewModelState(
    val errorMessage: String = "",
    val loginButtonEnable: Boolean = false
)

object LoginViewModelStateSucces : LoginViewModelState()
class LoginViewModelStateError(errorMessage: String) : LoginViewModelState(
    errorMessage = errorMessage,
    loginButtonEnable = true
)

class LoginViewModel : ViewModel() {
    private val state = MutableLiveData<LoginViewModelState>()
    fun getState(): LiveData<LoginViewModelState> = state

    fun login(username: String, password: String) {
        if (username == "kotlin" && password == "rocks") {
            state.value = LoginViewModelStateSucces
        } else {
            state.value =
                LoginViewModelStateError("Wrong username /password ($username, $password)")
        }
    }

}