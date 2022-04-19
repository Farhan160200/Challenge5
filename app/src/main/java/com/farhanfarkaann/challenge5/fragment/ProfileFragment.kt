package com.farhanfarkaann.challenge5.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var _binding : FragmentProfileBinding
    private val binding get() = _binding!!

    lateinit var prefFile : SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefFile = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val username = prefFile.getString("USERNAME", "")
        val email = prefFile.getString("EMAILIS","")
        val password = prefFile.getString("PASSWORD","")

        binding.etNewUsername.setText(username.toString())
        binding.etNewEmail.setText(email.toString())
        binding.etNewPassword.setText(password.toString())

        binding.btnUpdateProfile.setOnClickListener{

            val username : String = binding.etNewUsername.text.toString()
            val emailIsi : String = binding.etNewEmail.text.toString()
            val password : String  = binding.etNewPassword.text.toString()
            val myEdit: SharedPreferences.Editor = prefFile.edit()
            myEdit.putString("USERNAME",username)
            myEdit.putString("NEWEMAIL",emailIsi)
            myEdit.putString("PASSWORD",password)
            myEdit.commit()
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)

        }

        binding.btnBackHome.setOnClickListener{
            AlertDialog.Builder(requireContext()).setPositiveButton("Ya") { p0, p1 ->
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment2)

                val myEdit: SharedPreferences.Editor = prefFile.edit()
                myEdit.putBoolean("CHECKBOX",false)
                myEdit.apply()

//                editor.clear()
//                editor.apply()
                Toast.makeText(context, "anda berhasil logout", Toast.LENGTH_SHORT).show()
            }.setNegativeButton("Tidak"
            ) { p0, p1 ->
                p0.dismiss()
            }
                .setMessage("Apakah Anda Yakin ingin Logout").setTitle("Konfirmasi Logout").create().show()
//            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }



    }

}