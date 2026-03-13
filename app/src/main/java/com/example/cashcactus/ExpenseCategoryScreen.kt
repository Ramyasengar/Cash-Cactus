package com.example.cashcactus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ExpenseCategoryScreen(
    navController: NavHostController,
    age: Int,
    viewModel: MainViewModel
) {

    var food by remember { mutableStateOf("") }
    var rent by remember { mutableStateOf("") }
    var medical by remember { mutableStateOf("") }
    var emi by remember { mutableStateOf("") }
    var additional by remember { mutableStateOf("") }
    var education by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Monthly Expenses",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = food,
            onValueChange = { if (it.all { ch -> ch.isDigit() }) food = it },
            label = { Text("Food") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = rent,
            onValueChange = { if (it.all { ch -> ch.isDigit() }) rent = it },
            label = { Text("Rent") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = medical,
            onValueChange = { if (it.all { ch -> ch.isDigit() }) medical = it },
            label = { Text("Medical") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        if (age > 18) {
            OutlinedTextField(
                value = emi,
                onValueChange = { if (it.all { ch -> ch.isDigit() }) emi = it },
                label = { Text("EMI") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = additional,
            onValueChange = { if (it.all { ch -> ch.isDigit() }) additional = it },
            label = { Text("Additional") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = education,
            onValueChange = { if (it.all { ch -> ch.isDigit() }) education = it },
            label = { Text("Education") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {

                // Save values into ViewModel
                viewModel.food = food.toFloatOrNull() ?: 0f
                viewModel.rent = rent.toFloatOrNull() ?: 0f
                viewModel.medical = medical.toFloatOrNull() ?: 0f
                viewModel.emi = emi.toFloatOrNull() ?: 0f
                viewModel.additional = additional.toFloatOrNull() ?: 0f
                viewModel.education = education.toFloatOrNull() ?: 0f

                // Save to database
                viewModel.saveMonthlyExpenses()

                navController.navigate("expenseGraph")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}