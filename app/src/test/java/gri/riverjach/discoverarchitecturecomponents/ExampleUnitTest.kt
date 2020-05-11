package gri.riverjach.discoverarchitecturecomponents

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    // Par defaut un LiveData essaie de s'excuter dans une boucle donc il faut mettre
    // une boucle pour les tests InstantTaskExecutorRule
    // Champs obligatoire pour le mettre en Statique
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //Pour Mocker Log ou class Static
    @Before
    fun mockAllUriInteractions() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.v(any(), any()) } returns 0
    }

    @Test
    fun shouldLoadUserWithIdEquals_1() {
        val viewModel = UserViewModel()
        val userId = 1
        val liveData = viewModel.getUser(userId)

        assertEquals(userId, liveData.value!!.id)
    }
}
