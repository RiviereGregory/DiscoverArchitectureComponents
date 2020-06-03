package gri.riverjach.discoverarchitecturecomponents

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, Bookmark::class, Folder::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun folderDao(): FolderDao
    abstract fun bookmarkDao(): BookmarkDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE 'user' ADD email TEXT NOT NULL DEFAULT ''")
    }
}