package com.example.cashcactus

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var loginFailed by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineMedium
                )

                // ---------------- EMAIL FIELD ----------------

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError =
                            it.isNotEmpty() &&
                                    !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        loginFailed = false
                    },
                    label = { Text("Email") },
                    isError = emailError,
                    keyboardOptions =
                        KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                if (emailError) {
                    Text(
                        text = "Invalid email format",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // ---------------- PASSWORD FIELD ----------------

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.isNotEmpty() && it.length < 6
                        loginFailed = false
                    },
                    label = { Text("Password") },
                    isError = passwordError,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation =
                        if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisible = !passwordVisible
                        }) {
                            Icon(
                                imageVector =
                                    if (passwordVisible)
                                        Icons.Filled.Visibility
                                    else
                                        Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    }
                )

                if (passwordError) {
                    Text(
                        text = "Password must be at least 6 characters",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                if (loginFailed) {
                    Text(
                        text = "Invalid email or password",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // ---------------- LOGIN BUTTON ----------------

                Button(
                    onClick = {

                        val validEmail =
                            Patterns.EMAIL_ADDRESS.matcher(email).matches()

                        val validPassword = password.length >= 6

                        emailError = !validEmail
                        passwordError = !validPassword

                        if (validEmail && validPassword) {

                            viewModel.loginUser(
                                email = email,
                                password = password
                            ) { success ->

                                if (success) {

                                    Toast.makeText(
                                        context,
                                        "Login Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate("home") {
                                        popUpTo("login") {
                                            inclusive = true
                                        }
                                    }

                                } else {
                                    loginFailed = true
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }

                // ---------------- GO TO REGISTER ----------------

                TextButton(
                    onClick = {
                        navController.navigate("register")
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Don't have an account? Register")
                }
            }
        }
    }
}