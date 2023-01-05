package com.meprogrammer.chichmic.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.meprogrammer.chichmic.R
import com.meprogrammer.chichmic.databinding.FragmentProfileBinding
import com.meprogrammer.chichmic.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding  //defining the binding class
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProfile()
        bindObservers()

    }

    private fun getProfile() {
        viewModel.getUser()
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            it?.let {


                binding.userData = it.data
                val uri: Uri = Uri.parse(it.data?.image!!)
                binding.ivProfileImage.setImageURI(uri)

            }
        }

    }

    private fun bindObservers() {


        binding.ivEdit.setOnClickListener {

            val bundle = Bundle()
            bundle.putBoolean("edit", true)
            findNavController().navigate(R.id.action_ProfileFragment_to_EditDetailsFragment, bundle)

        }

    }


}