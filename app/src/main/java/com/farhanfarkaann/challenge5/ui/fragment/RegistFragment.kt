package com.farhanfarkaann.challenge5.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.databinding.FragmentRegistBinding
import com.farhanfarkaann.challenge5.data.room.entity.User
import com.farhanfarkaann.challenge5.viewmodeluser.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class RegistFragment : Fragment() {
//
//    private var _binding: FragmentRegistBinding ? = null
//    private val binding get() = _binding!!
//    private val authViewModel : AuthViewModel by viewModels()
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentRegistBinding.inflate(inflater,container,false)
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        checkUserData()
//
//
//        binding.btnDaftar.setOnClickListener {
//            findNavController().navigate(R.id.action_registFragment_to_loginFragment)
//        }
//        clickListenerSignUp()
//    }
//
//    private fun checkUserData() {
//        authViewModel.resultRegister.observe(viewLifecycleOwner) {
//            if (it != null) {
//                if (it != 0.toLong()) {
//                    Toast.makeText(requireContext(), "Registration success", Toast.LENGTH_SHORT)
//                        .show()
////                    val bundle = Bundle().apply {
////                        putString(LoginFragment.USERNAME, binding.etUsername.text.toString())
////                    }
//                    findNavController().navigate(
//                        R.id.action_registFragment_to_loginFragment,
////                        bundle
//                    )
//                } else {
//                    Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//        }
//
//    }
//
//    private fun clickListenerSignUp(){
//        binding.btnDaftar.setOnClickListener {
//            // disini bawa data username untuk login
//            if (binding.etPassword.text.toString() != binding.etConfirmPass.text.toString()) {
//                binding.tfConfirmPass.error = "Password tidak sama"
//                binding.etConfirmPass.text?.clear()
//                binding.etConfirmPass.requestFocus()
//            } else {
//                val objectUser = User(
//                    null,
//                    binding.etUsername.text.toString(),
//                    binding.etEmail.text.toString(),
//                    binding.etPassword.text.toString(),"")
//                authViewModel.register(objectUser)
//            }
//        }
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
//}