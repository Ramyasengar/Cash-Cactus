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
fun RegisterScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var registerFailed by remember { mutableStateOf(false) }
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
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium
                )

                // ---------------- NAME ----------------

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                // ---------------- EMAIL ----------------

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError =
                            it.isNotEmpty() &&
                                    !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        registerFailed = false
                    },
                    label = { Text("Email") },
                    isError = emailError,
                    keyboardOptions =
                        KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                if (emailError) {
                    Text(
                        text = "Enter valid email format",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // ---------------- PASSWORD ----------------

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.isNotEmpty() && it.length < 6
                        registerFailed = false
                    },
                    label = { Text("Password (min 6 chars)") },
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

                if (registerFailed) {
                    Text(
                        text = "Email already exists",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // ---------------- REGISTER BUTTON ----------------

                Button(
                    onClick = {

                        val validEmail =
                            Patterns.EMAIL_ADDRESS.matcher(email).matches()

                        val validPassword = password.length >= 6
                        val validName = name.isNotBlank()

                        emailError = !validEmail
                        passwordError = !validPassword

                        if (validName && validEmail && validPassword) {

                            viewModel.register(
                                name = name,
                                email = email,
                                password = password
                            ) { success ->

                                if (success) {

                                    Toast.makeText(
                                        context,
                                        "Registration Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate("login") {
                                        popUpTo("register") {
                                            inclusive = true
                                        }
                                    }

                                } else {
                                    registerFailed = true
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Register")
                }

                // ---------------- GO TO LOGIN ----------------

                TextButton(
                    onClick = {
                        navController.navigate("login")
                    },
                    modifier =
                        Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Already registered? Login")
                }
            }
        }
    }
}