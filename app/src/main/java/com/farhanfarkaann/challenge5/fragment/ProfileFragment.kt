package com.farhanfarkaann.challenge5.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.farhanfarkaann.challenge5.MainViewModel
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.databinding.FragmentProfileBinding
import com.farhanfarkaann.challenge5.room.entity.User
import com.farhanfarkaann.challenge5.ui.UpdateViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var prefFile: SharedPreferences
    private val args: ProfileFragmentArgs by navArgs()
    private val updateViewModel: UpdateViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private var imageUri: Uri? = null


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data
                    imageUri =data?.data
                    imageUri?.let { loadImage(it) }
                    loadImage(fileUri)

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateViewModel.getDataUser()
        mainViewModel.getDataUser()
        prefFile = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)




        updateViewModel.user.observe(viewLifecycleOwner) {
            binding.etNewUsername.setText(it.username)
            binding.etNewEmail.setText(it.email)
            binding.etNewPassword.setText(it.password)
            val photo  = it.avatar
//            val myEdit: SharedPreferences.Editor = prefFile.edit()
//            myEdit.putString("PHOTO",photo)
//            myEdit.apply()
            val photo2 = prefFile.getString("PHOTO", photo)
            binding.ivLogo.setImageURI(photo2?.toUri())
//            binding.ivLogo.setImageURI(it.avatarPath?.toUri())
//            if (it?.avatarPath!=""){
//                if (imageUri!=null){
//                    binding.ivLogo.setImageURI(imageUri)
//                }else{
//                    binding.ivLogo.setImageURI(it.avatarPath?.toUri())
//                }

//            }
        }
        setClickListeners()
        updateUser()
        logout()
    }

    private fun logout() {


        binding.btnBackHome.setOnClickListener {

        AlertDialog.Builder(requireContext()).setPositiveButton("Ya") { p0, p1 ->

            mainViewModel.deleteUserPref()
                    mainViewModel.user.observe(viewLifecycleOwner) {
                        val myEdit: SharedPreferences.Editor = prefFile.edit()
                        myEdit.remove("PHOTO").apply()
                            findNavController().navigate(R.id.action_profileFragment_to_loginFragment2)
                    }

            Toast.makeText(context, "anda berhasil logout", Toast.LENGTH_SHORT).show()
        }
            .setNegativeButton(
                "Tidak"
            ) { p0, p1 ->
                p0.dismiss()

            }
            .setMessage("Apakah Anda Yakin ingin Logout").setTitle("Konfirmasi Logout")
            .create().show()

        }
    }

    private fun updateUser() {


        binding.btnUpdateProfile.setOnClickListener {
            val imageProfile = if (imageUri==null){
                ""
            }else{
                imageUri.toString()
            }

//            val photo = prefFile.getString("PHOTO", "")

                val user = User(
                    args.user.id,
                    binding.etNewUsername.text.toString(),
                    binding.etNewEmail.text.toString(),
                    binding.etNewPassword.text.toString(),
                    imageProfile
//                    photo
                    )


                updateViewModel.update(user)
                updateViewModel.setDataUser(user)
                updateViewModel.getDataUser()
                observeUpdate()
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
            Toast.makeText(context, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show()
            }

    }



private fun observeUpdate() {
    updateViewModel.resultUpdate.observe(viewLifecycleOwner) {
        if (it != null) {
            if (it != 0) {
                Toast.makeText(
                    requireContext(), "User berhasil diupdate",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(requireContext(), "User gagal diupdate", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}


    private fun setClickListeners() {

        binding.ivLogo.setOnClickListener {

            openImagePicker()
        }
    }





    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            ) //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }
    private fun loadImage(uri: Uri?) {
//        uri?.let {
//            val photo = it.toString()
//            val myEdit: SharedPreferences.Editor = prefFile.edit()
//            myEdit.putString("PHOTO",photo)
//            myEdit.apply()
//            Log.d("main", "saveToLocal: ${photo.toUri()}}")
//            binding.ivLogo.setImageURI(it)
//        }

            binding.ivLogo.setImageURI(uri)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
