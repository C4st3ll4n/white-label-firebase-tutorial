package br.com.castellan.whitelabel.ui.addproducts

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.castellan.whitelabel.R
import br.com.castellan.whitelabel.domain.model.Product
import br.com.castellan.whitelabel.domain.usecase.CreateProductUseCase
import br.com.castellan.whitelabel.util.fromCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {

    private val _imageUiErrorResId = MutableLiveData<Int>()
    val imageUriErrorResId: LiveData<Int> = _imageUiErrorResId


    private val _descriptionUiErrorResId = MutableLiveData<Int>()
    val descriptionUriErrorResId: LiveData<Int?> = _descriptionUiErrorResId


    private val _priceUiErrorResId = MutableLiveData<Int>()
    val priceUriErrorResId: LiveData<Int?> = _priceUiErrorResId


    private val _productCreated = MutableLiveData<Product>()
    val productCreated: LiveData<Product> = _productCreated


    private var isFormValid = false

    fun createProduct(description: String, price: String, imageUri: Uri?) {
        viewModelScope.launch {
            isFormValid = true

            _imageUiErrorResId.value = getDrawableResIdIfNull(imageUri)
            _descriptionUiErrorResId.value = getErrorStringResIdIfEmpty(description)
            _priceUiErrorResId.value = getErrorStringResIdIfEmpty(price)


            if (isFormValid) {
                try {
                    val product =
                        createProductUseCase(description, price.fromCurrency(), imageUri!!)
                    _productCreated.value = product
                } catch (e: Exception) {
                    Log.e("AddProductViewModel", "createProduct: DEU RUIM")
                }
            }

        }
    }

    private fun getErrorStringResIdIfEmpty(value: String): Int? {
        return if (value.isEmpty()) {
            isFormValid = false
            R.string.add_product_field_error
        } else null
    }

    private fun getDrawableResIdIfNull(value: Uri?): Int {
        return if (value == null) {
            isFormValid = false
            R.drawable.background_product_image_error
        } else R.drawable.background_product_image
    }
}