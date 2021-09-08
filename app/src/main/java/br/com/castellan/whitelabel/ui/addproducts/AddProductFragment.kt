package br.com.castellan.whitelabel.ui.addproducts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.castellan.whitelabel.R
import br.com.castellan.whitelabel.databinding.AddProductFragmentBinding

class AddProductFragment : Fragment() {
    lateinit var binding:AddProductFragmentBinding
    private lateinit var viewModel: AddProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


}