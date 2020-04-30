package gri.riverjach.discoverarchitecturecomponents

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class MainFragment:Fragment() {
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // test ViewModel dans Fragment
        Log.d("MainFragment", "onCreate()")
        // Avec le this on passe par le init() du ViewModel donc on le recrée
        //viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // Solution le rattaché à une activity
        viewModel = ViewModelProvider(activity!!).get(UserViewModel::class.java)

        // Fin test ViewModel dans Fragment //

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainFragment", "onDestroy()")
    }
}