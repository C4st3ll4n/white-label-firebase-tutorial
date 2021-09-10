package br.com.castellan.whitelabel.config.di

import br.com.castellan.whitelabel.config.Config
import br.com.castellan.whitelabel.config.ConfigImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface ConfigModule {
    @Binds
    fun bindConfig(configImpl: ConfigImpl): Config
}