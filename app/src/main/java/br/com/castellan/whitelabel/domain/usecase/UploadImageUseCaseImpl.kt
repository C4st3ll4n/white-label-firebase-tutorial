package br.com.castellan.whitelabel.domain.usecase

import android.net.Uri
import br.com.castellan.whitelabel.data.ProductRepository

class UploadImageUseCaseImpl(private val productRepository: ProductRepository) :
    UploadProductImageUseCase {
    override suspend fun invoke(imageUri: Uri): String =
        productRepository.uploadProductImage(imageUri)
}