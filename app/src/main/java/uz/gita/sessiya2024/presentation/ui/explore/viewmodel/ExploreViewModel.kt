package uz.gita.sessiya2024.presentation.ui.explore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.sessiya2024.data.model.BookData

interface ExploreViewModel {
    val loadBookListLiveData: LiveData<List<BookData>>
    fun loadBooks()
    abstract fun searchFun(text: String)


    val onProgress:LiveData<Boolean>
    val onExeption:LiveData<Boolean>
    val onExeptionString:LiveData<String>
    val searchListLiveData: MutableLiveData<List<BookData>>
}
