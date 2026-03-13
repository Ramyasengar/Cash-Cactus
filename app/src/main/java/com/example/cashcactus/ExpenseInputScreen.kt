package com.example.cashcactus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseInputScreen(
    navController: NavHostController,
    category: String,
    age: Int
) {

    var amount by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf(false) }

    val baseCategories = listOf(
        "Food",
        "Rent",
        "Medical",
        "Additional",
        "Education"
    )

    val categories = if (age > 18) {
        baseCategories + "EMI"
    } else {
        baseCategories
    }

    val currentIndex = categories.indexOf(category)
    val isLast = currentIndex == categories.lastIndex

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$category Expense") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Enter amount for $category",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        amount = it
                    }
                },
                label = { Text("Amount") },
                isError = amountError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            if (amountError) {
                Text(
                    "Enter valid numeric amount",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (!isLast) {
                val nextCategory = categories[currentIndex + 1]
                navController.navigate("expenseInput/$nextCategory/$age")
            } else {
                navController.navigate("expenseGraph")
            }


        }
    }
}