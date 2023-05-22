package uz.gita.sessiya2024.domain.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.data.model.MessageData
import uz.gita.sessiya2024.data.model.ProfilData
import uz.gita.sessiya2024.data.model.TypeData

interface BookUseCase {
    suspend fun getAllBooks(): Result<List<TypeData>>
    fun downloadBook(context: Context, data: BookData): Flow<Result<BookData>>

    fun getNewBooks(): List<BookData>
    fun gerRecommendBooks(): List<BookData>

    fun getFullBookList():List<BookData>

    fun getProfilINformation():Flow<Result<ProfilData>>

    fun addComment(message: MessageData):Flow<Result<String>>
    fun search(text: String): List<BookData>
}