package uz.gita.sessiya2024.data.model

import uz.gita.sessiya2024.data.local.entity.BookEntity
import java.io.Serializable

data class BookData(
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
    var isActive:Boolean,
    var description:String
): Serializable {
    fun toEntity(unv_title:String = "", unv_logo:String = ""):BookEntity{
        return BookEntity(
           id, name, pdf_reference, cover_reference,kurs, year, owner, date, university_short, reference, 0, unv_title, unv_logo,description
            )
    }
}
