package br.com.castellan.whitelabel.domain.usecase

import android.net.Uri
import br.com.castellan.whitelabel.domain.model.Product

interface GetProductUseCase {
    suspend operator fun invoke():List<Product>
}