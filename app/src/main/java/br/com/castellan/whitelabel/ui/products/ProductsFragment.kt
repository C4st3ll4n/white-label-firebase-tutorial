package br.com.castellan.whitelabel.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.castellan.whitelabel.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private val productsAdapter = ProductsAdapter()

    private val viewModel: ProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observerVMEvents()
        viewModel.getProducts()
    }

    private fun observerVMEvents() {
        viewModel.productsData.observe(viewLifecycleOwner, productsAdapter::submitList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    private fun setRecyclerView() {
        binding.recyclerViewProducts.run {
            setHasFixedSize(true)
            adapter = productsAdapter

        }
    }
}