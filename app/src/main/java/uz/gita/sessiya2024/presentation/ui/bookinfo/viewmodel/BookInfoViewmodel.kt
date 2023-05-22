package uz.gita.sessiya2024.presentation.ui.bookinfo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.sessiya2024.data.model.BookData

interface BookInfoViewmodel {

    val fileDownloadedLiveData: LiveData<BookData>
    val errorDownloadLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>

    fun downloadBook(context: Context, data: BookData)
}