package br.com.castellan.whitelabel.domain.usecase

import android.net.Uri
import br.com.castellan.whitelabel.data.ProductRepository
import javax.inject.Inject

class UploadImageUseCaseImpl @Inject constructor(private val productRepository: ProductRepository) :
    UploadProductImageUseCase {
    override suspend fun invoke(imageUri: Uri): String =
        productRepository.uploadProductImage(imageUri)
}