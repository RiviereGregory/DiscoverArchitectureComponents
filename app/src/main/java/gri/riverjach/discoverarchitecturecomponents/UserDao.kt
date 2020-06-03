package gri.riverjach.discoverarchitecturecomponents

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    /**
     * Usage:
     * dao.insertUser(user)
     */
    @Insert
    fun insertUser(user: User)

    /**
     * dao.getAllUsers().observe(this, Observer { users -> ... })
     */
    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>

    /**
     * dao.getUserById(3).observe(this, Observer { user -> ... })
     * on a LiveData car room fait de l'asynchrone donc on observe.
     */
    @Query("SELECT * FROM user WHERE id= :userId")
    fun getUserById(userId: Int): LiveData<User>
}