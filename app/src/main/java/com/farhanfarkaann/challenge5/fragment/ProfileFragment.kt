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
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.databinding.FragmentProfileBinding
import com.farhanfarkaann.challenge5.utils.PermissionUtils
import com.farhanfarkaann.challenge5.utils.PermissionUtils.isPermissionsGranted
import com.farhanfarkaann.challenge5.utils.StorageUtils
import java.util.*


class ProfileFragment : Fragment() {
    private lateinit var _binding : FragmentProfileBinding
    private val binding get() = _binding!!

    lateinit var prefFile : SharedPreferences
    ///////////////////////////////////////////

//    private val cameraResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val bitmap = result.data?.extras?.get("data") as Bitmap
//                val uri = StorageUtils.savePhotoToExternalStorage(
//                    contentResolver = null,
//                    UUID.randomUUID().toString(),
//                    bitmap
//                )
//                uri?.let {
//                    loadImage(it)
//                }
//            }
//        }
//
    private fun loadImage(uri: Uri) {
        binding.ivLogo.setImageURI(uri)
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
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        ////////////////////////////












///////////////////////////////////
        prefFile = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val username = prefFile.getString("USERNAME", "")
        val email = prefFile.getString("EMAILIS","")
        val password = prefFile.getString("PASSWORD","")
        val photo = prefFile.getString("PHOTO","")

        binding.etNewUsername.setText(username.toString())
        binding.etNewEmail.setText(email.toString())
        binding.etNewPassword.setText(password.toString())
//        binding.ivLogo.setImageResource(photo!!.toInt())

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

        binding.btnBackHome.setOnClickListener{
            AlertDialog.Builder(requireContext()).setPositiveButton("Ya") { p0, p1 ->
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment2)

                val myEdit: SharedPreferences.Editor = prefFile.edit()
                myEdit.putBoolean("CHECKBOX",false)
                myEdit.apply()

                Toast.makeText(context, "anda berhasil logout", Toast.LENGTH_SHORT).show()
            }.setNegativeButton("Tidak"
            ) { p0, p1 ->
                p0.dismiss()
            }
                .setMessage("Apakah Anda Yakin ingin Logout").setTitle("Konfirmasi Logout").create().show()
        }



    }
    private fun setClickListeners() {

        binding.ivLogo.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireContext()!!,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }


    }

    private fun openGallery() {
//        intent.type = "image/*"
        galleryResult.launch("image/*")
    }
//    private fun openCamera() {
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        findNavController().navigate(MediaStore.ACTION_IMAGE_CAPTURE)
//        cameraResult.launch(cameraIntent)
//    }


//    private fun getRequiredPermission(): Array<String> {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//        } else {
//            arrayOf(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.CAMERA
//            )
//        }
//    }

}