package gri.riverjach.discoverarchitecturecomponents

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import kotlinx.android.synthetic.main.activity_folder.*
import java.util.concurrent.Executors

fun EditText.textString(): String {
    return this.text.toString()
}

class FolderActivity : AppCompatActivity() {
    private lateinit var folderDao: FolderDao
    private lateinit var bookmarkDao: BookmarkDao

    private val folderNameLiveData = MutableLiveData<String>()
    private lateinit var getFolderLiveData: LiveData<Folder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)

        folderDao = App.database.folderDao()
        bookmarkDao = App.database.bookmarkDao()

        getFolderLiveData = Transformations.switchMap(folderNameLiveData) { folderName ->
            folderDao.getFolderByName(folderName)
        }

        bookmarkDao.getAllBookmarks().observe(this, Observer { bookmarks ->
            bookmarksTextView.text = bookmarks!!
                .joinToString("\n") { bookmark -> bookmark.toString() }
        })

        getFolderLiveData.observe(this, Observer { folder ->
            createBookmark(
                folder!!.id,
                bookmarkNameEditText.textString(),
                bookmarkUrlEditText.textString()
            )
        })

        createFolderButton.setOnClickListener {
            createFolder(createFolderEditText.textString())
        }
        createBookmarkButton.setOnClickListener {
            folderNameLiveData.value = bookmarkFolderNameEditText.textString()
        }
    }

    private fun createBookmark(folderId: Long, bookmarkName: String, bookmarkUrl: String) {
        Executors.newSingleThreadExecutor().execute {
            val bookmark = Bookmark(folderId = folderId, name = bookmarkName, url = bookmarkUrl)
            bookmarkDao.insertBookmark(bookmark)
        }

        Toast.makeText(
            this,
            "Bookmark $bookmarkName created!",
            Toast.LENGTH_SHORT
        ).show()

    }

    private fun createFolder(folderName: String) {
        Executors.newSingleThreadExecutor().execute {
            folderDao.insertFolder(Folder(name = folderName))
        }

        Toast.makeText(
            this,
            "Folder $folderName created!",
            Toast.LENGTH_SHORT
        ).show()

        bookmarkFolderNameEditText.setText(folderName, TextView.BufferType.EDITABLE)
    }

}