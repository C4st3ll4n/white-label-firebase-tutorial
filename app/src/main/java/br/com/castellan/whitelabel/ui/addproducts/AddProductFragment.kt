package br.com.castellan.whitelabel.ui.addproducts

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import br.com.castellan.whitelabel.databinding.AddProductFragmentBinding
import br.com.castellan.whitelabel.util.CurrencyTextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddProductFragment : BottomSheetDialogFragment() {
    lateinit var binding: AddProductFragmentBinding
    private lateinit var viewModel: AddProductViewModel
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
        setupListerners()
    }

    private fun setupListerners() {
        binding.imageProduct.setOnClickListener {
            choseImage()
        }

        binding.buttonAddProduct.setOnClickListener {
            val description = binding.inputDescription.text.toString()
            val price = binding.inputPrice.text.toString()
        }

        binding.inputPrice.run {
            addTextChangedListener(CurrencyTextWatcher(this))
        }
    }

    private fun choseImage(){
        getContent.launch("image/*")
    }


}