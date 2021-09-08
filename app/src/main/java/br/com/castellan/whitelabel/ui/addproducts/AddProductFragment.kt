package br.com.castellan.whitelabel.ui.addproducts

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.fragment.app.viewModels
import br.com.castellan.whitelabel.databinding.AddProductFragmentBinding
import br.com.castellan.whitelabel.util.CurrencyTextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : BottomSheetDialogFragment() {
    lateinit var binding: AddProductFragmentBinding
    private val viewModel: AddProductViewModel by viewModels()
    private var imageUri: Uri? = null
    private val getContent = registerForActivityResult(GetContent()) { uri ->
        imageUri = uri
        binding.imageProduct.setImageURI(imageUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVMEvents()
        setupListerners()
    }

    private fun observeVMEvents() {
        viewModel.imageUriErrorResId.observe(viewLifecycleOwner) { resId ->
            binding.imageProduct.setBackgroundResource(resId)
        }

        viewModel.priceUriErrorResId.observe(viewLifecycleOwner) {
            binding.inputLayoutPrice.setError(it)
        }

        viewModel.descriptionUriErrorResId.observe(viewLifecycleOwner) {
            binding.inputLayoutDescription.setError(it)
        }
    }

    private fun TextInputLayout.setError(stringResId: Int?) {
        error = if (stringResId != null) {
            getString(stringResId)
        } else null
    }

    private fun setupListerners() {
        binding.imageProduct.setOnClickListener {
            choseImage()
        }

        binding.buttonAddProduct.setOnClickListener {
            val description = binding.inputDescription.text.toString()
            val price = binding.inputPrice.text.toString()

            viewModel.createProduct(description,price,imageUri)
        }

        binding.inputPrice.run {
            addTextChangedListener(CurrencyTextWatcher(this))
        }
    }

    private fun choseImage() {
        getContent.launch("image/*")
    }


}