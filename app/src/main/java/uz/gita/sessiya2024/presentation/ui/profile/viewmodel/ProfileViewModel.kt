package uz.gita.sessiya2024.presentation.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.sessiya2024.data.model.MessageData
import uz.gita.sessiya2024.data.model.ProfilData

interface ProfileViewModel {
    val profilLiveData: LiveData<ProfilData>
    fun getOwnerInfo()
    fun addComment(message:MessageData)

    val onProgress: LiveData<Boolean>
    val onExeption: LiveData<Boolean>
    val onExeptionString: LiveData<String>


    val messageString: MutableLiveData<String>

    var boolean:Boolean
}
