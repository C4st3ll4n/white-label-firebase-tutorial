package br.com.castellan.whitelabel.domain.usecase

import br.com.castellan.whitelabel.data.ProductRepository
import br.com.castellan.whitelabel.domain.model.Product
import javax.inject.Inject

class GetProductUseCaseImpl @Inject constructor(private val productRepository: ProductRepository):GetProductUseCase {
    override suspend fun invoke(): List<Product> = productRepository.getProducts()
}