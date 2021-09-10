package br.com.castellan.whitelabel.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import br.com.castellan.whitelabel.R
import br.com.castellan.whitelabel.databinding.FragmentProductsBinding
import br.com.castellan.whitelabel.domain.model.Product
import br.com.castellan.whitelabel.util.PRODUCT_KEY
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
        setupListeners()
        setRecyclerView()
        observerNavBackStack()
        observerVMEvents()
        loadProducts()
    }

    private fun setupListeners() {

        with(binding) {
            fabAdd.setOnClickListener {
                findNavController().navigate(R.id.action_productsFragment_to_addProductFragment)
            }

            swipeProducts.run {
                setOnRefreshListener(this@ProductsFragment::loadProducts)
            }
        }

    }

    private fun loadProducts() {
        viewModel.getProducts()
    }

    private fun observerNavBackStack() {
        findNavController().run {
            val backStack = getBackStackEntry(R.id.productsFragment)
            val savedStateHandle = backStack.savedStateHandle

            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME && savedStateHandle.contains(PRODUCT_KEY)) {
                    val product = savedStateHandle.get<Product>(PRODUCT_KEY)

                    val oldList = productsAdapter.currentList
                    val newList = oldList.toMutableList().apply {
                        add(product)
                    }

                    productsAdapter.submitList(newList)
                    binding.recyclerViewProducts.smoothScrollToPosition(newList.size - 1)
                    savedStateHandle.remove<Product>(PRODUCT_KEY)
                }
            }

            backStack.lifecycle.addObserver(observer)

            viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    backStack.lifecycle.removeObserver(observer)
                }
            })
        }
    }

    private fun observerVMEvents() {
        viewModel.productsData.observe(
            viewLifecycleOwner,
            {
                productsAdapter.submitList(it)
                binding.swipeProducts.isRefreshing = false

            }
        )

        viewModel.addButtonVisibilityData.observe(viewLifecycleOwner,
            { binding.fabAdd.visibility = it })

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