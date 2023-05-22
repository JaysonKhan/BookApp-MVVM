package uz.gita.sessiya2024.data.model

import java.io.Serializable

data class TypeData(
    val id: Long,
    val iconUrl: String,
    val title:String,
    val isActive:Boolean,
    val list: List<BookData>
):Serializable
