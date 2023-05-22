package uz.gita.sessiya2024.presentation.ui.read.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class ReadViewModelImpl @Inject constructor(
    private val useCase: BookUseCase
): ViewModel(), ReadViewModel {

    override fun saveBookAsRead(data: BookData, currentPage: Int) {
        Log.d("TTT", "Saving Book in ReadViewModel: $data")
    }
}
