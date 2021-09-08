package br.com.castellan.whitelabel.domain.usecase

import android.net.Uri

interface UploadProductImageUseCase {

    suspend operator fun invoke(imageUri:Uri):String
}