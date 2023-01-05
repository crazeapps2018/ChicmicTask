package com.meprogrammer.chichmic.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.meprogrammer.chichmic.R
import com.meprogrammer.chichmic.databinding.FragmentAccountDetailBinding
import com.meprogrammer.chichmic.model.UserTable
import com.meprogrammer.chichmic.util.showToast
import com.meprogrammer.chichmic.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AccountDetailFragment : Fragment() {


    private lateinit var binding: FragmentAccountDetailBinding  //defining the binding class
    private lateinit var viewModel: MainViewModel
    private var mProfileUri: Uri? = null
    private val gender = listOf("Male", "Female")
    private var edit: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            edit = it.getBoolean("edit")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {        // Inflate the layout for this fragment
        binding = FragmentAccountDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val genderAdapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, gender)
        binding.inputGender.setAdapter(genderAdapter)

        setEditData()

        bindListeners()


    }

    private fun setEditData() {
        viewModel.getUser()
        if (edit) {
            binding.tvHeader.text = "Edit Account"

            viewModel.userLiveData.observe(viewLifecycleOwner) {
                it?.let {

                    binding.userData = it.data
                    try {
                        mProfileUri = Uri.parse(it.data?.image!!)
                        binding.ivProfileImage.setImageURI(mProfileUri)
                    } catch (e: Exception) {
                    }

                }
            }

        }
    }

    private fun bindListeners() {

        binding.inputGender.setOnClickListener {
            binding.inputGender.showDropDown()
        }

        binding.ivEditImage.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.ivBackBtn.setOnClickListener {
            if (edit) {
                activity!!.supportFragmentManager.popBackStack()
            } else {
                activity!!.finish()
            }
        }

        binding.btnSave.setOnClickListener {
            val firstName = binding.edFirstName.text.toString()
            val lastName = binding.edLastName.text.toString()
            val email = binding.edEmail.text.toString()
            val phone = binding.edPhone.text.toString()
            val age = binding.inputGender.text.toString()
            val dob = binding.inputDob.text.toString()

            if (isValid()) {
                viewModel.saveUser(
                    UserTable(
                        firstName = firstName, lastName = lastName, email = email, phone = phone,
                        gender = age, dob = dob, image = mProfileUri.toString()
                    )
                )
                findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
            }
        }


        val newCalendar = Calendar.getInstance();

        val StartTime = DatePickerDialog(
            context!!,
            { view, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth

                if (year <= 2005) {
                    binding.tvDobError.visibility = View.VISIBLE
                } else {
                    binding.inputDob.setText(
                        dayOfMonth.toString() + " / " + (monthOfYear + 1) + " / "
                                + year
                    );
                    binding.tvDobError.visibility = View.INVISIBLE
                }
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )


        binding.inputDob.setOnClickListener {
            StartTime.show()
        }


    }


    private fun isValid(): Boolean {
        when {
            mProfileUri == null -> context!!.showToast("Please select profile image")
            binding.edFirstName.text!!.isBlank() -> context!!.showToast("Please enter first name")
            binding.edLastName.text!!.isBlank() -> context!!.showToast("Please enter last name")
            binding.edEmail.text!!.isBlank() -> context!!.showToast("Please enter email")
            !(Patterns.EMAIL_ADDRESS.matcher(binding.edEmail.text!!)
                .matches()) -> context!!.showToast("Please enter valid email")
            binding.edPhone.text!!.isBlank() -> context!!.showToast("Please enter phone")
            binding.inputGender.text!!.isBlank() -> context!!.showToast("Please select gender")
            binding.inputGender.text!!.equals("Select") -> context!!.showToast("Please select gender")
            binding.inputDob.text!!.isBlank() -> context!!.showToast("Please enter DOB")
            binding.inputDob.text!!.equals("Select") -> context!!.showToast("Please enter DOB")

            else -> return true
        }
        return false
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                mProfileUri = fileUri
                binding.ivProfileImage.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                context!!.showToast(ImagePicker.getError(data))
            } else {
                context!!.showToast("Task Cancelled")
            }
        }


}