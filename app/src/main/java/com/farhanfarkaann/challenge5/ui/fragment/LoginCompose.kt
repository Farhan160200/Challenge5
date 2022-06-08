package com.farhanfarkaann.challenge5.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
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

                                ImageWithBackground(
                                    painter = painterResource(id = R.drawable.wallpapers),
                                    backgroundDrawableResId = R.drawable.wallpapers,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .height(2580.dp)
                                        .width(2960.dp)
                                        .padding(0.dp),
                                )
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
            Spacer(modifier = Modifier.height(26.dp))
            Text(text = "IDLIK", fontSize = 50.sp,style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                color = Color.DarkGray)
            Text(text = "Sign In!", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_undraw_working_late_pukg),
                contentDescription = "Image App",
                modifier = Modifier.size(200.dp, 200.dp),
                contentScale = ContentScale.Fit
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
            Spacer(modifier = Modifier.height(16.dp))



            OutlinedTextField(


//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Green,
//                    unfocusedBorderColor = White),

                value = username,
                onValueChange = { username = it },
                placeholder = { Text(text = "Username") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(22.dp)),
                shape = RoundedCornerShape(22.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "password") },
//                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(22.dp)),
                shape = RoundedCornerShape(22.dp),
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

    @Composable
    fun ImageWithBackground(
        painter: Painter,
        @DrawableRes backgroundDrawableResId: Int,
        contentDescription: String?,
        modifier: Modifier = Modifier,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.FillBounds,
        alpha: Float = DefaultAlpha,
        colorFilter: ColorFilter? = null
    ) {
        Box(
            modifier = modifier
        ) {
            Image(
                painter = painterResource(backgroundDrawableResId),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
            )
            Image(
                painter = painter,
                contentDescription = contentDescription,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                modifier = Modifier
                    .matchParentSize()
            )
        }
    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {

        MyTheme {
            ImageWithBackground(
                painter = painterResource(id = R.drawable.wallpapers),
                backgroundDrawableResId = R.drawable.wallpapers,
                contentDescription = "",
                modifier = Modifier
                    .height(2580.dp)
                    .width(2960.dp)
                    .padding(0.dp)
            )
            Column {

                HeaderLogin()
                ActionItem()
            }
        }
    }
}
