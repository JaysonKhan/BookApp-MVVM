package uz.gita.sessiya2024.domain.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.data.model.MessageData
import uz.gita.sessiya2024.data.model.ProfilData
import uz.gita.sessiya2024.data.model.TypeData
import uz.gita.sessiya2024.domain.repository.BookREpositoryImpl
import uz.gita.sessiya2024.domain.repository.BookRepository
import javax.inject.Inject

class BookUseCaseImpl @Inject constructor(
    private val repository: BookRepository
):BookUseCase {

    override suspend fun getAllBooks(): Result<List<TypeData>> = repository.getAllBooks()

    override fun downloadBook(context: Context, data: BookData): Flow<Result<BookData>> = repository.downloadBook(context, data)

    override fun getNewBooks(): List<BookData> = repository.getNewBooks()

    override fun gerRecommendBooks(): List<BookData> = repository.gerRecommendBooks()

    override fun getFullBookList(): List<BookData> = repository.getFullBookList()

    override fun getProfilINformation(): Flow<Result<ProfilData>> = repository.getProfilINformation()
    override fun addComment(message: MessageData): Flow<Result<String>> = repository.addComment(message)
    override fun search(text: String): List<BookData> = repository.getLikeBooks(text)


}