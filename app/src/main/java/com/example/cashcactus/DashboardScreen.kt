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
fun DashboardScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {

    var age by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var savingValue by remember { mutableStateOf("") }

    // Dropdown states
    var categoryExpanded by remember { mutableStateOf(false) }
    var savingTypeExpanded by remember { mutableStateOf(false) }

    var category by remember { mutableStateOf("") }
    var savingType by remember { mutableStateOf("") }

    val categories = listOf(
        "Student",
        "Working",
        "Business",
        "Housewife"
    )

    val savingTypes = listOf(
        "Amount",
        "Percentage"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Text(
            text = "Setup Your Budget",
            style = MaterialTheme.typography.headlineMedium
        )

        // CATEGORY DROPDOWN
        ExposedDropdownMenuBox(
            expanded = categoryExpanded,
            onExpandedChange = { categoryExpanded = !categoryExpanded }
        ) {

            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = categoryExpanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false }
            ) {

                categories.forEach {

                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            category = it
                            categoryExpanded = false
                        }
                    )
                }
            }
        }

        // AGE
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // SALARY
        OutlinedTextField(
            value = salary,
            onValueChange = { salary = it },
            label = { Text("Monthly Salary") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // SAVING TYPE DROPDOWN
        ExposedDropdownMenuBox(
            expanded = savingTypeExpanded,
            onExpandedChange = { savingTypeExpanded = !savingTypeExpanded }
        ) {

            OutlinedTextField(
                value = savingType,
                onValueChange = {},
                readOnly = true,
                label = { Text("Saving Type") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = savingTypeExpanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = savingTypeExpanded,
                onDismissRequest = { savingTypeExpanded = false }
            ) {

                savingTypes.forEach {

                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            savingType = it
                            savingTypeExpanded = false
                        }
                    )
                }
            }
        }

        // SAVING VALUE
        OutlinedTextField(
            value = savingValue,
            onValueChange = { savingValue = it },
            label = { Text("Saving Value") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                viewModel.dashboardCategory = category
                viewModel.dashboardAge = age.toIntOrNull() ?: 0
                viewModel.monthlySalary = salary.toFloatOrNull() ?: 0f
                viewModel.savingType = savingType
                viewModel.savingValue = savingValue.toFloatOrNull() ?: 0f

                viewModel.saveDashboardData()

                navController.navigate(
                    "expenseCategory/${viewModel.dashboardAge}"
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}