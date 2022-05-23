package com.farhanfarkaann.challenge5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.databinding.FragmentDetailBinding
import com.farhanfarkaann.challenge5.databinding.FragmentRegistBinding
import com.farhanfarkaann.challenge5.room.database.UserDatabase
import com.farhanfarkaann.challenge5.room.entity.User
import com.farhanfarkaann.challenge5.viewmodeluser.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class RegistFragment : Fragment() {

    private var _binding: FragmentRegistBinding ? = null
    private val binding get() = _binding!!
    private val authViewModel : AuthViewModel by viewModels()
    companion object{
        const val   CONFPASSWORD = "CONFPASSWORD"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ngecekResult()


        binding.btnDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_registFragment_to_loginFragment)
        }
        signUpListner()
    }

    private fun ngecekResult() {
        authViewModel.resultRegister.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it != 0.toLong()) {
                    Toast.makeText(requireContext(), "Registration success", Toast.LENGTH_SHORT)
                        .show()
                    val bundle = Bundle().apply {
                        putString(LoginFragment.USERNAME, binding.etUsername.text.toString())
                    }
                    findNavController().navigate(
                        R.id.action_registFragment_to_loginFragment,
                        bundle
                    )
                } else {
                    Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun signUpListner(){
        binding.btnDaftar.setOnClickListener {
            // disini bawa data username untuk login
            if (binding.etPassword.text.toString() != binding.etConfirmPass.text.toString()) {
                binding.tfConfirmPass.error = "Password tidak sama"
                binding.etConfirmPass.text?.clear()
                binding.etConfirmPass.requestFocus()
            } else {
                val objectUser = User(
                    null,
                    binding.etUsername.text.toString(),
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                authViewModel.register(objectUser)
            }
        }
    }
//                GlobalScope.async {
//                    val result = myDb?.userDao()?.addUser(objectUser)
//                    runBlocking(Dispatchers.Main) {
//                        if (result !=0.toLong()){
//                            Toast.makeText(requireContext(), "Registration success", Toast.LENGTH_SHORT).show()
//                            val bunlde = Bundle().apply {
//                                putString(LoginFragment.USERNAME, binding.etUsername.text.toString())
//                            }
//                            findNavController().navigate(R.id.action_registFragment_to_loginFragment,bunlde)
//                        }else{
//                            Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//        }

//        val sharedPreferences : SharedPreferences =
//            requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

//        binding.btnDaftar.setOnClickListener {
//            GlobalScope.async {
//                val username: String = binding.etUsername.text.toString()
//                val emailIsi: String = binding.etEmail.text.toString()
//                val password: String = binding.etPassword.text.toString()
//                val confPassword: String = binding.etConfirmPass.text.toString()
//                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//
//                runBlocking(Dispatchers.Main) {
//                    when {
//                        username.isEmpty() -> {
//                            binding.tfUsername.error =
//                                getString(com.google.android.material.R.string.error_icon_content_description)
//                        }
//                        emailIsi.isEmpty() -> {
//                            binding.tfEmail.error = "Tidak Boleh Kosong!"
//                        }
//                        password.isEmpty() -> {
//                            binding.tfPassword.error = "Tidak Bolek Kosong!"
//                        }
//                        confPassword.isEmpty() -> {
//                            binding.tfConfirmPass.error = " Tidak Boleh Kosong!"
//                        }
//                        password != confPassword -> {
//                            Toast.makeText(context, "Password Tidak Sama ", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                        else -> {
//                            editor.putString("USERNAME", username)
//                            editor.putString("EMAILIS", emailIsi)
//                            editor.putString("PASSWORD", password)
//                            editor.putString(CONFPASSWORD, confPassword)
//                            editor.apply()
//                            Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
//
//
//                            findNavController().navigate(R.id.action_registFragment_to_loginFragment)
//
//                        }
//                    }
//                }
//            }
//        }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}