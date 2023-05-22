package uz.gita.sessiya2024.presentation.ui.list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.data.model.TypeData
import uz.gita.sessiya2024.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class ScreenListViewModelImpl @Inject constructor(
    private val useCase: BookUseCase
):ViewModel(), ScreenListViewMOdel {
    override val loadBookListLiveData = MutableLiveData<List<BookData>>()

    override val onProgress = MutableLiveData<Boolean>()
    override val onExeption = MutableLiveData<Boolean>()
    override val onExeptionString = MutableLiveData<String>()

    override fun loadBooks(list: List<BookData>) {
        onProgress.value = true
        viewModelScope.launch {
            if (list.isNullOrEmpty()){
                onExeption.value = true
                onExeptionString.value = "Ma'lumotlar topilmadi :("
                onProgress.value = false
            }else{
                onProgress.value = false
                onExeption.value = false
                loadBookListLiveData.value = list
            }

        }
    }

}