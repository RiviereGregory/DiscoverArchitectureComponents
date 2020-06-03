package gri.riverjach.discoverarchitecturecomponents

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface NoteDao {

    @Query(""" SELECT * FROM note
        INNER JOIN utilisateur ON utilisateur.id = note.userId
        WHERE utilisateur.id = :userId        
    """)
    fun getNotesForUserId(userId: Int):LiveData<List<Note>>
}