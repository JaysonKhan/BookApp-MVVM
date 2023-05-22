package uz.gita.sessiya2024.presentation.ui.read.viewmodel

import uz.gita.sessiya2024.data.model.BookData

interface ReadViewModel {
    fun saveBookAsRead(data: BookData, currentPage: Int)
}
