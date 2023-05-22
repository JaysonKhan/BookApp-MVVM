package uz.gita.sessiya2024.presentation.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.data.model.TypeData
import uz.gita.sessiya2024.domain.usecase.BookUseCase
import uz.gita.sessiya2024.domain.usecase.BookUseCaseImpl
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val useCase: BookUseCase
): ViewModel(), HomeViewModel {
    override val recommendedBooksLiveData = MutableLiveData<List<TypeData>>()
    override val onProgress = MutableLiveData<Boolean>()
    override val onExeption = MutableLiveData<Boolean>()
    override val onExeptionString = MutableLiveData<String>()

    override fun getRecommendedBooks() {
        onProgress.value = true
        viewModelScope.launch {
            useCase.getAllBooks()
                .onSuccess {
                    it.sortedBy {type ->
                        type.id
                    }
                    recommendedBooksLiveData.value = it
                    onProgress.value = false
                    onExeption.value = false
                }
                .onFailure {
                    onProgress.value = false
                    onExeption.value = true
                    onExeptionString.value = it.message
                }
        }



    }
}
