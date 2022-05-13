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
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.farhanfarkaann.challenge5.MainViewModel
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.databinding.FragmentLoginBinding
import com.farhanfarkaann.challenge5.datastore.DataStoreSetting
import com.farhanfarkaann.challenge5.datastore.dataStore
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_regist.*

import kotlinx.coroutines.*

//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
class LoginFragment : Fragment() {

     lateinit var binding: FragmentLoginBinding
     lateinit var dataStoreSetting: DataStoreSetting

    lateinit var prefFile : SharedPreferences

    var isRemembered = false
    var check = false
    companion object{
        const val EMAILISI  = "EMAIL"
        const val PASSWORD = "PASSWORD"
        const val USERNAME = "USERNAME"
        const val   CONFPASSWORD = "CONFPASSWORD"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root



    }

//    fun saveToLocal() {
//        binding.apply {
//            btnLogin.setOnClickListener {
//                if (etEmail.text!!.isNotEmpty() && etEmail.text!!.isNotEmpty() )
//
//                {
//
//                    mainViewModelData.saveToLocal(binding.checkbox.isChecked )
//
//                } else {
//                    Toast.makeText(context, "fill the field", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStoreSetting = DataStoreSetting(requireContext().dataStore)

        buttonSave()
        observeData()




        prefFile =  requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//        isRemembered = prefFile.getBoolean("CHECKBOX",false)



        val email2 = prefFile.getString("EMAILIS","")
        val username = prefFile.getString("USERNAME","")
        val pass2 = prefFile.getString("PASSWORD","")

//        if (isRemembered){
//            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//        }



        binding.btnLogin.setOnClickListener {
            GlobalScope.async {
                val email: String = binding.etEmail.text.toString()
                val password: String = binding.etPassword.text.toString()
//                val checked: Boolean = binding.checkbox.isChecked
                val editor: SharedPreferences.Editor = prefFile.edit()
//                editor.putBoolean("CHECKBOX", checked)
                editor.apply()
                runBlocking(Dispatchers.Main) {
                    if (email.isEmpty() && password.isEmpty()) {
                        Toast.makeText(context, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()

                    } else {
                        when {
                            email2 != email -> {
                                binding.tfEmail.error = "Email tidak Terdaftar."
                            }
                            pass2 != password -> {
                                binding.tfPassword.error = "Password  yang anda masukan salah."
                            }

                            else -> {
                                Toast.makeText(
                                    context,
                                    "Selamat Datang $username",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            }
                        }
                    }
                }

                binding.tvRegisterSwitch.setOnClickListener {
                    findNavController().navigate(R.id.action_loginFragment_to_registFragment)
                }
            }
        }
    }

    private fun observeData() {
            dataStoreSetting.userRememberedMe.asLiveData().observe(viewLifecycleOwner) {

                if (it == true) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

            }
        }
    }
//    private fun ngeCheck (){
//        isRemembered = false
//        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//
//    }

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
