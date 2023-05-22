package uz.gita.sessiya2024.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.sessiya2024.data.local.BookDatabase
import uz.gita.sessiya2024.data.local.dao.BookDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworModule {

    @Provides
    @Singleton
    fun provideFireStorage(): StorageReference = Firebase.storage.reference

    @Provides
    @Singleton
    fun provideFireDataBase(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideBookDatabase():BookDatabase = BookDatabase.getInstance()

    @Provides
    @Singleton
    fun provideDao(db:BookDatabase): BookDao = db.getBookDao()
}