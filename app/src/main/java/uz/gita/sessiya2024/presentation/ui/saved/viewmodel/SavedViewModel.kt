package uz.gita.sessiya2024.presentation.ui.saved.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.sessiya2024.data.model.TypeData

interface SavedViewModel {

    fun getUniversities()

    val univerLiveData: LiveData<List<TypeData>>

    val onProgress:LiveData<Boolean>
    val onExeption:LiveData<Boolean>
    val onExeptionString:LiveData<String>

}
