package uz.gita.sessiya2024.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.sessiya2024.domain.usecase.BookUseCase
import uz.gita.sessiya2024.domain.usecase.BookUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bindUseCase(impl: BookUseCaseImpl):BookUseCase
}