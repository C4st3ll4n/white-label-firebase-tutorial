package br.com.castellan.whitelabel.domain.usecase.di

import br.com.castellan.whitelabel.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {
    @Binds
    fun bindCreateProductUseCase(createProductUseCase: CreateProductUseCaseImpl): CreateProductUseCase
    @Binds
    fun bindGetProductUseCase(getProductUseCase: GetProductUseCaseImpl): GetProductUseCase
    @Binds
    fun bindUploadImageUseCase(uploadProductImageUseCase: UploadImageUseCaseImpl): UploadProductImageUseCase
}