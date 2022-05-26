package com.farhanfarkaann.challenge5.fragment

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
import com.farhanfarkaann.challenge5.databinding.FragmentLoginBinding
import com.farhanfarkaann.challenge5.data.room.database.UserDatabase
import com.farhanfarkaann.challenge5.viewmodeluser.AuthViewModel
import com.farhanfarkaann.challenge5.viewmodeluser.UserManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var myDb: UserDatabase? = null
     private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel : AuthViewModel by viewModels()

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
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root



    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        userLogin()
        setUsername()
        myDb = UserDatabase.getInstance(requireContext())
        binding.tvRegisterSwitch.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registFragment)
        }

        binding.btnLogin.setOnClickListener {
            authViewModel.login(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString(),
            )


        }
        loginCheck()


    }

    private fun loginCheck() {
        authViewModel.resultLogin.observe(viewLifecycleOwner){ user->
            if (user==null){
                val snackbar = Snackbar.make(
                    binding.root, "Gagal masuk mungkin anda salah memasukan email atau password",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.setAction("Oke") {
                    snackbar.dismiss()
                    binding.etUsername.requestFocus()
                    binding.etUsername.text!!.clear()
                    binding.etPassword.text!!.clear()
                }
                snackbar.show()
            } else {
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
                authViewModel.setDataUser(user)
                if (findNavController().currentDestination?.id == R.id.loginFragment){
                    findNavController().navigate(navigateHome)

                }
            }
        }

    }


    private fun userLogin() {
        authViewModel.getDataUser()
        authViewModel.user.observe(viewLifecycleOwner) {
            if (it.id != UserManager.DEFAULT_ID && findNavController().currentDestination?.id == R.id.loginFragment) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
    private fun setUsername() {
        val username = arguments?.getString(USERNAME)
        if (username.isNullOrEmpty()) {
            binding.etUsername.hint = null
        } else {
            binding.etUsername.setText(username)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
