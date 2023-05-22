package uz.gita.sessiya2024.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.sessiya2024.data.model.BookData

@Entity(tableName = "Books")
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Long = 0,
    var name:String,
    var pdf_reference:String,
    var cover_reference:String,
    var kurs:Long,
    var year:Long,
    var owner: String,
    var date: String,
    var university_short: String,
    var reference: String,
    var isnewbookread: Int = 0,
    var unv_title:String,
    var unv_logo:String,
    var description:String
){
    fun toBookData():BookData{
        return BookData(
            id, name, pdf_reference, cover_reference,kurs,  year, owner, date, university_short, reference, true, description
        )
    }

}
