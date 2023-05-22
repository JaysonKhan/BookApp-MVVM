package uz.gita.sessiya2024.presentation.ui.newbooks.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.sessiya2024.data.local.entity.BookEntity
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.data.model.TypeData

interface NewBooksViewMOdel {
     val loadBookListLiveData: LiveData<List<BookEntity>>
     fun loadBooks()
     fun updateBook(bookEntity: BookEntity)


     val onProgress:LiveData<Boolean>
     val onExeption:LiveData<Boolean>
     val onExeptionString:LiveData<String>
}