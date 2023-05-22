package uz.gita.sessiya2024.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.data.model.TypeData

interface HomeViewModel {

    fun getRecommendedBooks()

    val recommendedBooksLiveData: LiveData<List<TypeData>>

    val onProgress:LiveData<Boolean>
    val onExeption:LiveData<Boolean>
    val onExeptionString:LiveData<String>
}
