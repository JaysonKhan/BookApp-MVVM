package uz.gita.sessiya2024.presentation.ui.saved.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.sessiya2024.data.model.TypeData
import uz.gita.sessiya2024.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class SavedViewModelImpl @Inject constructor(
    private val useCase: BookUseCase
) : ViewModel(), SavedViewModel {
    override val univerLiveData = MutableLiveData<List<TypeData>>()

    override val onProgress = MutableLiveData<Boolean>()
    override val onExeption = MutableLiveData<Boolean>()

    override val onExeptionString = MutableLiveData<String>()

    override fun getUniversities() {
        onProgress.value = true
        viewModelScope.launch {
            useCase.getAllBooks().onSuccess {
                onProgress.value = false
                onExeption.value = false
                univerLiveData.value = it
            }.onFailure {
                onProgress.value = false
                onExeption.value = true
                onExeptionString.value = it.message
            }
        }
    }

}
