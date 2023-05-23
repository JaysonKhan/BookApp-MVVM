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
           id = id, name = name, pdf_reference = pdf_reference, cover_reference = cover_reference, kurs = kurs,
            year = year, owner = owner, date = date, university_short = university_short, reference = reference,
            unv_title = unv_title, unv_logo = unv_logo, description = description
            )
    }
}
