package uz.gita.sessiya2024.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.sessiya2024.domain.repository.BookREpositoryImpl
import uz.gita.sessiya2024.domain.repository.BookRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun bindBookRepository(impl: BookREpositoryImpl):BookRepository
}