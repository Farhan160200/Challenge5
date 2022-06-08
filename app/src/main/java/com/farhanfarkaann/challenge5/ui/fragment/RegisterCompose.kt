package com.farhanfarkaann.challenge5.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.data.datastore.UserManager
import com.farhanfarkaann.challenge5.data.room.entity.User
import com.farhanfarkaann.challenge5.viewmodeluser.AuthViewModel
import com.farhanfarkaann.mycomposeapp.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterCompose  : Fragment()  {
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
                    setContent {
                        MyTheme {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colors.background
                            ) {
                                Column {
                                    HeaderLogin()
                                    ActionItem()

                                }
                            }
                        }
                    }

            }
        }

/////////////////////////////////

    @Composable
    fun HeaderLogin() {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Register", fontSize = 36.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_undraw_working_late_pukg),
                contentDescription = "Image App",
                modifier = Modifier.size(200.dp, 100.dp),
                contentScale = ContentScale.Crop
            )
        }
    }






    @Composable
    fun ActionItem() {

        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        var password by  remember { mutableStateOf("") }
        var confPassword by  remember { mutableStateOf("") }

        var passwordVisibility by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))



            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text(text = "Username") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "password") },
//                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff, ""
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confPassword,
                onValueChange = { confPassword = it },
                placeholder = { Text(text = "Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff, ""
                        )
                    }
                })

        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (username == "" || email == "" || password == "" || confPassword == "") {
                        AlertDialog.Builder(requireContext())
                            .setTitle("")
                            .setMessage("Semua kolom harus diisi")
                            .setPositiveButton("Coba login kembali") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    } else if (password != confPassword) {
                        Toast.makeText(
                            requireContext(),
                            "Password konfirmasi tidak sama",
                            Toast.LENGTH_LONG
                        ).show()
                        confPassword = ""
                    } else {
                        val user = User(null, username, email, password,"")
                        authViewModel.register(user)
                        authViewModel.resultRegister.observe(viewLifecycleOwner) {
                            if (it != null) {
                                if (it != 0.toLong()) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Registration success",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    findNavController().navigate(
                                        R.id.action_registCompose_to_loginCompose
                                    )
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Registration failed",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
            )




             {
                Text(text = "Daftar", style = TextStyle(Color.White))

            }
        }


    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {

        MyTheme {
            Column {
                HeaderLogin()
                ActionItem()

            }

        }
    }
}
