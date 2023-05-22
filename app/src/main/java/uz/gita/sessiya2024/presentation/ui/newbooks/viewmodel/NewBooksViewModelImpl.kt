package uz.gita.sessiya2024.presentation.ui.newbooks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.sessiya2024.data.local.dao.BookDao
import uz.gita.sessiya2024.data.local.entity.BookEntity
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.data.model.TypeData
import uz.gita.sessiya2024.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class NewBooksViewModelImpl @Inject constructor(
    private val bookDao: BookDao
):ViewModel(), NewBooksViewMOdel {
    override val loadBookListLiveData = MutableLiveData<List<BookEntity>>()

    override val onProgress = MutableLiveData<Boolean>()
    override val onExeption = MutableLiveData<Boolean>()
    override val onExeptionString = MutableLiveData<String>()

    override fun loadBooks() {
        onProgress.value = true
        viewModelScope.launch {
            val result = bookDao.getNewBooks()
            if (result.isNullOrEmpty()){
                onExeption.value = true
                onExeptionString.value = "Yangi qo'shilgan kitoblar yo'q :)"
            }else{
                onExeption.value = false
                loadBookListLiveData.value =result
            }
            onProgress.value = false
        }

    }

    override fun updateBook(bookEntity: BookEntity) {
        bookDao.updateBook(bookEntity)
    }

}