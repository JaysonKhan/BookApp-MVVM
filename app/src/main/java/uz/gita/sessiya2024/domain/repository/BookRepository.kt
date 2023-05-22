package uz.gita.sessiya2024.domain.repository

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.data.model.MessageData
import uz.gita.sessiya2024.data.model.ProfilData
import uz.gita.sessiya2024.data.model.TypeData

interface BookRepository {
    suspend fun getAllBooks(): Result<List<TypeData>>
    fun downloadBook(context: Context, data: BookData): Flow<Result<BookData>>

    fun getNewBooks(): List<BookData>
    fun gerRecommendBooks(): List<BookData>

    fun  getFullBookList():List<BookData>

    fun getProfilINformation():Flow<Result<ProfilData>>

    fun addComment(message: MessageData):Flow<Result<String>>

    fun getLikeBooks(like:String):List<BookData>

}