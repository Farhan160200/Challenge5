package com.farhanfarkaann.challenge5.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.farhanfarkaann.challenge5.MainViewModel
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.databinding.FragmentLoginBinding
import com.farhanfarkaann.challenge5.datastore.DataStoreSetting
import com.farhanfarkaann.challenge5.datastore.dataStore
import com.farhanfarkaann.challenge5.room.database.UserDatabase
import com.farhanfarkaann.challenge5.viewmodeluser.HomeViewModel
import com.farhanfarkaann.challenge5.viewmodeluser.UserManager
import com.farhanfarkaann.challenge5.viewmodeluser.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


import kotlinx.coroutines.*


class LoginFragment : Fragment() {

    private var myDb: UserDatabase? = null
     lateinit var binding: FragmentLoginBinding
     lateinit var dataStoreSetting: DataStoreSetting
    private lateinit var userManager: UserManager
    private lateinit var viewModel: HomeViewModel

    lateinit var prefFile : SharedPreferences

    var isRemembered = false
    var check = false
    companion object{
        const val EMAILISI  = "EMAIL"
        const val PASSWORD = "PASSWORD"
        const val USERNAME = "username"
        const val   CONFPASSWORD = "CONFPASSWORD"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root



    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStoreSetting = DataStoreSetting(requireContext().dataStore)

        buttonSave()
        observeData()
        setUsername()
        userManager = UserManager(requireContext())
        viewModel =
            ViewModelProvider(requireActivity(), ViewModelFactory(userManager))[HomeViewModel::class.java]
//        userLogin()
        userManager = UserManager(requireContext())
        myDb = UserDatabase.getInstance(requireContext())
        binding.tvRegisterSwitch.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registFragment)
        }



        binding.btnLogin.setOnClickListener {
            GlobalScope.async {
                val result = myDb?.userDao()?.getUser(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )
                runBlocking(Dispatchers.Main) {
                    if (result == null) {
                        val snackbar = Snackbar.make(
                            it, "Gagal masuk mungkin anda salah memasukan email atau password",
                            Snackbar.LENGTH_INDEFINITE
                        )
                        snackbar.setAction("Oke") {
                            snackbar.dismiss()
                            binding.etUsername.requestFocus()
                            binding.etUsername.text!!.clear()
                            binding.etPassword.text!!.clear()
                        }

                        snackbar.show()
                    }else {
                        Toast.makeText(
                            requireContext(),
                            "Selamat datang ${binding.etUsername.text.toString()}",
                            Toast.LENGTH_LONG
                        ).show()
                        val navigateHome =
                            LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                                binding.etUsername.text.toString(),
                                binding.etPassword.text.toString()
                            )
                        findNavController().navigate(navigateHome)
                    }
                }
                if (result != null){
                    viewModel.setDataUser(result)
                }
            }
        }

    }

//    private fun userLogin() {
//        viewModel.apply {
//            getDataUser().observe(viewLifecycleOwner){
//                if (it.id != UserManager.DEFAULT_ID){
//                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                }
//            }
//        }
//    }
    private fun setUsername() {
        val username = arguments?.getString(USERNAME)
        if (username.isNullOrEmpty()) {
            binding.etUsername.hint = null
        } else {
            binding.etUsername.setText(username)
        }
    }



//        prefFile =  requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
////        isRemembered = prefFile.getBoolean("CHECKBOX",false)
//
//
//
//        val email2 = prefFile.getString("EMAILIS","")
//        val username = prefFile.getString("USERNAME","")
//        val pass2 = prefFile.getString("PASSWORD","")
//
////        if (isRemembered){
////            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
////        }
//
//
//
//        binding.btnLogin.setOnClickListener {
//            GlobalScope.async {
//                val email: String = binding.etEmail.text.toString()
//                val password: String = binding.etPassword.text.toString()
////                val checked: Boolean = binding.checkbox.isChecked
//                val editor: SharedPreferences.Editor = prefFile.edit()
////                editor.putBoolean("CHECKBOX", checked)
//                editor.apply()
//                runBlocking(Dispatchers.Main) {
//                    if (email.isEmpty() && password.isEmpty()) {
//                        Toast.makeText(context, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
//
//                    } else {
//                        when {
//                            email2 != email -> {
//                                binding.tfEmail.error = "Email tidak Terdaftar."
//                            }
//                            pass2 != password -> {
//                                binding.tfPassword.error = "Password  yang anda masukan salah."
//                            }
//
//                            else -> {
//                                Toast.makeText(
//                                    context,
//                                    "Selamat Datang $username",
//                                    Toast.LENGTH_SHORT
//                                )
//                                    .show()
//                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                            }
//                        }
//                    }
//                }
//
//                binding.tvRegisterSwitch.setOnClickListener {
//                    findNavController().navigate(R.id.action_loginFragment_to_registFragment)
//                }
//            }
//        }


    private fun observeData() {
            dataStoreSetting.userRememberedMe.asLiveData().observe(viewLifecycleOwner) {

                if (it == true && binding.etPassword.text!!.isNotEmpty()) {
                    binding.btnLogin.setOnClickListener {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
            }
        }
    }


    private fun buttonSave() {

        //Gets the user input and saves it
        binding.checkbox.setOnClickListener {

            val checked: Boolean = binding.checkbox.isChecked

            //Stores the values
            GlobalScope.launch {
                dataStoreSetting.storeUser(checked)


            }
        }
    }
}
