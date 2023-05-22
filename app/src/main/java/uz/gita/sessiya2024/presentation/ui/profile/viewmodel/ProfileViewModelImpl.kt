package uz.gita.sessiya2024.presentation.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.sessiya2024.data.model.MessageData
import uz.gita.sessiya2024.data.model.ProfilData
import uz.gita.sessiya2024.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val useCase: BookUseCase
) : ViewModel(), ProfileViewModel {
    override val profilLiveData = MutableLiveData<ProfilData>()

    override fun getOwnerInfo() {
        onProgress.value = true
        useCase.getProfilINformation().onEach {
            it.onSuccess { info ->
                profilLiveData.value = info
                onProgress.value = false
                onExeption.value =false
            }
            it.onFailure { exeption ->
                onExeptionString.value = exeption.message
                onProgress.value = false
                onExeption.value =true
            }
            }.launchIn(viewModelScope)
    }

    override fun addComment(message: MessageData) {
        useCase.addComment(message).onEach {
            it.onSuccess { xabar->
                messageString.value = xabar
            }
            it.onFailure { error ->
                messageString.value = "Ma'lumot yuborishda xatolik :( \n ${error.message}"
            }
        }.launchIn(viewModelScope)
    }

    override val onProgress = MutableLiveData<Boolean>()
    override val onExeption = MutableLiveData<Boolean>()
    override val onExeptionString = MutableLiveData<String>()
    override val messageString = MutableLiveData<String>()
    override var boolean = false

}
