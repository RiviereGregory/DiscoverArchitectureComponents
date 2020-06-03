package gri.riverjach.discoverarchitecturecomponents

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_folder.*
import java.util.concurrent.Executors

fun EditText.textString(): String {
    return this.text.toString()
}

class FolderActivity : AppCompatActivity() {
    private lateinit var folderDao: FolderDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)

        folderDao = App.database.folderDao()

        createFolderButton.setOnClickListener {
            createFolder(createFolderEditText.textString())
        }
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