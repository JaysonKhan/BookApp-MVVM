package uz.gita.sessiya2024.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import uz.gita.sessiya2024.data.local.entity.BookEntity

@Dao
interface BookDao {
    @Insert
    fun addBook(note: BookEntity)

    @Query("SELECT * FROM Books ORDER by kurs")
    fun getBooks(): List<BookEntity>

    @Query("SELECT * FROM Books WHERE isnewbookread = 1")
    fun getNewBooks(): List<BookEntity>

    @Query("SELECT * FROM Books WHERE id =:id")
    fun getBook(id:Long): BookEntity

    /*

    @Query("SELECT * FROM person_table WHERE firstName LIKE :searchQuery OR lastName LIKE :searchQuery")
     */
    @Query("SELECT * FROM Books WHERE  name LIKE '%' || :like || '%'  OR kurs LIKE :like OR year LIKE :like OR university_short LIKE :like")
    fun getLikeBooks(like:String): List<BookEntity>

    @Update
    fun updateBook(note: BookEntity)



}