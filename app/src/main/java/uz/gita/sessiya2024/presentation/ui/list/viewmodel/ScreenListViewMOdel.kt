package uz.gita.sessiya2024.presentation.ui.list.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.data.model.TypeData

interface ScreenListViewMOdel {
     val loadBookListLiveData: LiveData<List<BookData>>
     fun loadBooks(list:List<BookData>)


     val onProgress:LiveData<Boolean>
     val onExeption:LiveData<Boolean>
     val onExeptionString:LiveData<String>
}