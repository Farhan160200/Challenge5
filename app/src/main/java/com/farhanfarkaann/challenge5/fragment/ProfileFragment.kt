package com.farhanfarkaann.challenge5.fragment
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.databinding.FragmentProfileBinding
import com.farhanfarkaann.challenge5.datastore.DataStoreSetting
import com.farhanfarkaann.challenge5.datastore.dataStore
import com.farhanfarkaann.challenge5.utils.StorageUtils
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.*
import java.io.File
import java.util.*


class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding

    lateinit var dataStoreSetting: DataStoreSetting
    lateinit var prefFile : SharedPreferences
    ///////////////////////////////////////////

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result.data?.extras?.get("data") as Bitmap
                val uri = StorageUtils.savePhotoToExternalStorage(
                    contentResolver = null,
                    UUID.randomUUID().toString(),
                    bitmap
                )
                uri?.let {
                    loadImage(it)
                }
            }
        }
//
//    private fun loadImage(uri: Uri) {
//        binding.ivLogo.setImageURI(uri)
//    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data
                    loadImage(fileUri)

                }
                ImagePicker.RESULT_ERROR -> {
//                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            prefFile = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
            val uri = result.toString()
            val myEdit: SharedPreferences.Editor = prefFile.edit()
            myEdit.putString("PHOTO",uri)
            myEdit.apply()
            loadImage(result)
        }





    ///////////////////////////////////
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStoreSetting = DataStoreSetting(requireContext().dataStore)
        setClickListeners()
        check()

        prefFile = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val username = prefFile.getString("USERNAME", "")
        val email = prefFile.getString("EMAILIS","")
        val password = prefFile.getString("PASSWORD","")
        val photo = prefFile.getString("PHOTO","")

        binding.ivLogo.isVisible

        binding.etNewUsername.setText(username.toString())
        binding.etNewEmail.setText(email.toString())
        binding.etNewPassword.setText(password.toString())
        binding.ivLogo.setImageURI(photo!!.toUri())
        binding.btnUpdateProfile.setOnClickListener{

            val username : String = binding.etNewUsername.text.toString()
            val emailIsi : String = binding.etNewEmail.text.toString()
            val password : String  = binding.etNewPassword.text.toString()
            val myEdit: SharedPreferences.Editor = prefFile.edit()
            myEdit.putString("USERNAME",username)
            myEdit.putString("NEWEMAIL",emailIsi)
            myEdit.putString("PASSWORD",password)
            myEdit.commit()
            Toast.makeText(context, "Sukses Update Profile", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)

        }

        binding.btnBackHome.setOnClickListener {
                AlertDialog.Builder(requireContext()).setPositiveButton("Ya") { p0, p1 ->
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment2)

                    val myEdit: SharedPreferences.Editor = prefFile.edit()
                    myEdit.putBoolean("CHECKBOX", false)
                    myEdit.apply()


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
    private fun setClickListeners() {
//        binding.ivLogo.setOnClickListener {
//            if (ActivityCompat.checkSelfPermission(requireContext()!!,
//                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                openGallery()
//            }
//        }
        binding.ivLogo.setOnClickListener {
            openImagePicker()
        }
    }

//    private fun openGallery() {
//        galleryResult.launch("image/*")
//    }
    private fun check() {

        //Stores the values
        GlobalScope.launch {
            dataStoreSetting.clearSession()
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
        uri?.let {
            val photo = it.toString()
            val myEdit: SharedPreferences.Editor = prefFile.edit()
            myEdit.putString("PHOTO",photo)
            myEdit.apply()
            Log.d("main", "saveToLocal: ${photo.toUri()}}")


            binding.ivLogo.setImageURI(it)
        }
    }
}