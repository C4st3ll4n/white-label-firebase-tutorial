package br.com.castellan.whitelabel.ui.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.castellan.whitelabel.config.Config
import br.com.castellan.whitelabel.domain.model.Product
import br.com.castellan.whitelabel.domain.usecase.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val config: Config
) :ViewModel(){

    private val _productsData = MutableLiveData<List<Product>>()
    val productsData: LiveData<List<Product>> = _productsData


    private val _addButtonVisibilityData = MutableLiveData(config.addButtonVisibility)
    val addButtonVisibilityData = _addButtonVisibilityData


    fun getProducts() = viewModelScope.launch {
        try{
            val products = getProductUseCase()
            _productsData.value = products
        }catch (e:Exception){
            Log.e("ProductsViewModel", "getProducts: Deu ruim", )
        }
    }
}