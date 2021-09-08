package br.com.castellan.whitelabel.domain.usecase

import br.com.castellan.whitelabel.data.ProductRepository
import br.com.castellan.whitelabel.domain.model.Product

class GetProductUseCaseImpl(private val productRepository: ProductRepository):GetProductUseCase {
    override suspend fun invoke(): List<Product> = productRepository.getProducts()
}