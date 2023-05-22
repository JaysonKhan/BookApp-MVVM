package uz.gita.sessiya2024.presentation.ui.bookinfo.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.domain.usecase.BookUseCase
import uz.gita.sessiya2024.domain.usecase.BookUseCaseImpl
import javax.inject.Inject

@HiltViewModel
class BookInfoViewmodelImpl @Inject constructor(
    private val useCase: BookUseCase
):ViewModel(), BookInfoViewmodel {
    override val fileDownloadedLiveData = MutableLiveData<BookData>()
    override val errorDownloadLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()

    override fun downloadBook(context: Context, data: BookData) {
        progressLiveData.value = true
        useCase.downloadBook(context, data).onEach { result ->
            result.onSuccess {
                fileDownloadedLiveData.value = it
                progressLiveData.value = false
            }
            result.onFailure {
                errorDownloadLiveData.value = it.message
                progressLiveData.value = false
            }
        }.launchIn(viewModelScope)
    }
}