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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.farhanfarkaann.challenge5.MainViewModel
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.databinding.FragmentProfileBinding
import com.farhanfarkaann.challenge5.data.datastore.DataStoreSetting
import com.farhanfarkaann.challenge5.data.datastore.dataStore
import com.farhanfarkaann.challenge5.room.database.UserDatabase
import com.farhanfarkaann.challenge5.room.entity.User
import com.farhanfarkaann.challenge5.ui.UpdateViewModel
import com.farhanfarkaann.challenge5.viewmodeluser.HomeViewModel
import com.farhanfarkaann.challenge5.viewmodeluser.UserManager
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var dataStoreSetting: DataStoreSetting
    lateinit var prefFile: SharedPreferences

    private val args: ProfileFragmentArgs by navArgs()
    private val updateViewModel: UpdateViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

//    lateinit var homeViewModel: HomeViewModel

    private lateinit var userManager: UserManager

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
            myEdit.putString("PHOTO", uri)
            myEdit.commit()
            loadImage(result)
        }


    ///////////////////////////////////
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
        dataStoreSetting = DataStoreSetting(requireContext().dataStore)
        prefFile = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        setClickListeners()
        logoutLogin()

        userManager = UserManager(requireContext())
        val photo = prefFile.getString("PHOTO", "")
        binding.ivLogo.setImageURI(photo!!.toUri())

//        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        updateViewModel.user.observe(viewLifecycleOwner) {
            binding.etNewUsername.setText(it.username)
            binding.etNewEmail.setText(it.email)
            binding.etNewPassword.setText(it.password)
        }
        updateUser()
        logout()
    }

    private fun logout() {
        binding.btnBackHome.setOnClickListener {
            val dialogKonfirmasi = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            dialogKonfirmasi.apply {
                setTitle("Logout")
                setMessage("Apakah anda yakin ingin log out?")
                setNegativeButton("Batal") { dialog, which ->
                    dialog.dismiss()
                }
                setPositiveButton("Ya") { dialog, which ->
                    dialog.dismiss()
                    mainViewModel.deleteUserPref()
                    mainViewModel.user.observe(viewLifecycleOwner) {
                            findNavController().navigate(R.id.action_profileFragment_to_loginFragment2)
                    }
                }
            }
            dialogKonfirmasi.show()
        }
    }

    private fun updateUser() {
        binding.btnUpdateProfile.setOnClickListener {
            val user = User(
                args.user.id,
                binding.etNewUsername.text.toString(),
                binding.etNewEmail.text.toString(),
                binding.etNewPassword.text.toString(),
            )
            updateViewModel.update(user)
            updateViewModel.setDataUser(user)
            updateViewModel.getDataUser()
            observeUpdate()
        }
    }



//        prefFile = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//        val username = prefFile.getString("USERNAME", "")
//        val email = prefFile.getString("EMAILIS","")
//        val password = prefFile.getString("PASSWORD","")
//        val photo = prefFile.getString("PHOTO","")
//
//        binding.ivLogo.isVisible
//
//        binding.etNewUsername.setText(username.toString())
//        binding.etNewEmail.setText(email.toString())
//        binding.etNewPassword.setText(password.toString())
//        binding.ivLogo.setImageURI(photo!!.toUri())
//        binding.btnUpdateProfile.setOnClickListener{
//
//            val username : String = binding.etNewUsername.text.toString()
//            val emailIsi : String = binding.etNewEmail.text.toString()
//            val password : String  = binding.etNewPassword.text.toString()
//            val myEdit: SharedPreferences.Editor = prefFile.edit()
//            myEdit.putString("USERNAME",username)
//            myEdit.putString("NEWEMAIL",emailIsi)
//            myEdit.putString("PASSWORD",password)
//            myEdit.commit()
//            Toast.makeText(context, "Sukses Update Profile", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
//
//        }

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
private fun logoutLogin() {
    binding.btnBackHome.setOnClickListener {
        check()
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

        binding.ivLogo.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openGallery() {
        galleryResult.launch("image/*")
    }

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
