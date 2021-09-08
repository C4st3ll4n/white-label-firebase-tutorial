package br.com.castellan.whitelabel.domain.usecase

import android.net.Uri
import br.com.castellan.whitelabel.domain.model.Product

interface CreateProductUseCase {

    suspend operator fun invoke(description:String, price:Double, imageUri: Uri):Product

}