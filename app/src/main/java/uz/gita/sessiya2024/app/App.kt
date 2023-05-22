package uz.gita.sessiya2024.app

import android.app.Application
import android.media.MediaPlayer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.onEach
import uz.gita.sessiya2024.data.local.BookDatabase
import uz.gita.sessiya2024.domain.repository.BookRepository
import javax.inject.Inject

@HiltAndroidApp
class App:Application(){
    override fun onCreate() {
        super.onCreate()
        BookDatabase.init(this)
    }

}