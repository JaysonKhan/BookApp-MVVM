package uz.gita.sessiya2024.presentation.ui.explore.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class ExploreViewModelImpl @Inject constructor(
    private val useCase: BookUseCase
): ViewModel(), ExploreViewModel {
    override val loadBookListLiveData = MutableLiveData<List<BookData>>()
    override val searchListLiveData = MutableLiveData<List<BookData>>()

    override val onProgress = MutableLiveData<Boolean>()
    override val onExeption = MutableLiveData<Boolean>()
    override val onExeptionString = MutableLiveData<String>()

    override fun loadBooks() {
        onProgress.value = true
        viewModelScope.launch {
            val list = useCase.getFullBookList().sortedBy {
                it.kurs
            }
            if (list.isNullOrEmpty()){
                onExeption.value = true
                onExeptionString.value = "Xozircha xechnima mavjud emas :)"
            }else{
                onExeption.value = false
                loadBookListLiveData.value = list
            }
            onProgress.value = false
        }
    }

    override fun searchFun(text: String) {

       searchListLiveData.value =  useCase.search(text).apply {
           if (isNullOrEmpty()){
               onExeption.value = true
               onExeptionString.value = "Bunday kitob mavjud emas :)"
           }else{
               onExeption.value = false
           }
       }
    }


}
