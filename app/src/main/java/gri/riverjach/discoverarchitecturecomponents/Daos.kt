package gri.riverjach.discoverarchitecturecomponents

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FolderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFolder(folder: Folder)

    @Query("SELECT * FROM folder WHERE name LIKE :folderName")
    fun getFolderByName(folderName: String) : LiveData<Folder>

}

@Dao
interface BookmarkDao{
    @Insert
    fun insertBookmark(bookmark: Bookmark) :Long

    @Query("SELECT * FROM bookmark")
    fun getAllBookmarks(): LiveData<List<Bookmark>>
}