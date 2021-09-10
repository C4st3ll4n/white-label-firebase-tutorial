package br.com.castellan.whitelabel.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.castellan.whitelabel.databinding.ItemProductBinding
import br.com.castellan.whitelabel.domain.model.Product
import br.com.castellan.whitelabel.util.toCurrency
import com.bumptech.glide.Glide

class ProductsAdapter: ListAdapter<Product, ProductsAdapter.ProductsVH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsVH = ProductsVH.create(parent)

    override fun onBindViewHolder(holder: ProductsVH, position: Int) = holder.bind(getItem(position))

    class ProductsVH(
        private val itemProductBinding: ItemProductBinding
    ) : RecyclerView.ViewHolder(itemProductBinding.root) {

        fun bind(product: Product) {
            itemProductBinding.run {
                Glide.with(itemView).load(product.imageUrl).fitCenter().into(imgProduct)

                textDescriptionProduct.text = product.description

                txtPriceProduct.text = product.price.toCurrency()
            }
        }

        companion object{
            fun create(parent:ViewGroup): ProductsVH{
                val itemProductBinding = ItemProductBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

                return ProductsVH(itemProductBinding)
            }
        }

    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>(){
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean  = oldItem.description == newItem.description
            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem

        }
    }

}