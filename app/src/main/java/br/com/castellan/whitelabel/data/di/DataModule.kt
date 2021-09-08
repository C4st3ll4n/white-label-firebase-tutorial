package br.com.castellan.whitelabel.data.di

import br.com.castellan.whitelabel.data.FirebaseProductDataSource
import br.com.castellan.whitelabel.data.ProductDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindDataSource(productDataSource: FirebaseProductDataSource):ProductDataSource
}