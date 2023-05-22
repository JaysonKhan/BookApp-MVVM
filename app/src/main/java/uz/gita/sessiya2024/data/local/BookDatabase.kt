package uz.gita.sessiya2024.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.sessiya2024.data.local.dao.BookDao
import uz.gita.sessiya2024.data.local.entity.BookEntity
import uz.gita.sessiya2024.data.model.BookData

@Database(entities = [BookEntity::class], version = 2, exportSchema = false)
abstract class BookDatabase:RoomDatabase() {
    abstract fun getBookDao():BookDao

    companion object {
        private lateinit var database: BookDatabase
        private const val NAME_DATABASE = "book_list1.db"

        fun init(context: Context){
            if(!(::database.isInitialized)){
                database = Room.databaseBuilder(context, BookDatabase::class.java, NAME_DATABASE)
                    .allowMainThreadQueries()
                    .build()
            }
        }
        fun getInstance() = database
    }
}