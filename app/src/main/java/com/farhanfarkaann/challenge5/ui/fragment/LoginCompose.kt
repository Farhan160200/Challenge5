package com.farhanfarkaann.challenge5.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.farhanfarkaann.challenge5.data.datastore.UserManager
import com.farhanfarkaann.challenge5.viewmodeluser.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.mycomposeapp.ui.theme.MyTheme

@AndroidEntryPoint
class LoginCompose : Fragment() {
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
            authViewModel.getDataUser()
            authViewModel.user.observe(viewLifecycleOwner) {
                if (it.id != UserManager.DEFAULT_ID &&
                    findNavController().currentDestination?.id == R.id.loginCompose
                ) {
                    findNavController().navigate(R.id.action_loginCompose_to_homeFragment)
                } else {
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
        }
    }
/////////////////////////////////

    @Composable
    fun HeaderLogin() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Login", fontSize = 36.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_undraw_working_late_pukg),
                contentDescription = "Image App",
                modifier = Modifier.size(300.dp, 150.dp),
                contentScale = ContentScale.Crop
            )
        }
    }


    @Composable
    fun ActionItem() {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
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

            Spacer(modifier = Modifier.height(32.dp))


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
                },
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Password,
//                    imeAction = ImeAction.Done
//                ),
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .fillMaxWidth()
//

            )


        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (username == "" || password == "") {
                        android.app.AlertDialog.Builder(requireContext())
                            .setTitle("")
                            .setMessage("Username atau Password tidak boleh kosong")
                            .setPositiveButton("Coba login kembali") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    } else {

                        authViewModel.login(username, password)
                        authViewModel.resultLogin.observe(viewLifecycleOwner) { user ->
                            if (user == null) {
                                Toast.makeText(
                                    requireContext(),
                                    "Login Gagal",
                                    Toast.LENGTH_LONG
                                ).show()
//                            android.app.AlertDialog.Builder(requireContext())
//                                .setTitle("")
//                                .setMessage("Gagal login mungkin anda salah memasukkan username atau password.")
//                                .setPositiveButton("Coba login kembali"){dialog,_ ->
//                                    dialog.dismiss()
//                                }
//                                .show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Selamat datang ${user.username}",
                                    Toast.LENGTH_LONG
                                ).show()
                                authViewModel.setDataUser(user)
                                if (findNavController().currentDestination?.id == R.id.loginCompose) {
                                    findNavController().navigate(R.id.action_loginCompose_to_homeFragment)
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
            ) {
                Text(text = "Login", style = TextStyle(Color.White))

            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Belum Pnya Akun",
                modifier = Modifier.clickable(
                    onClick = { findNavController().navigate(R.id.action_loginCompose_to_registCompose) }),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                color = Color.DarkGray

            )
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
