package com.example.cashcactus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Support") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            ExpandableSection(
                "Getting Started",
                "Register or log in using your mobile number or email.\n" +
                        "Set your monthly budget and financial goals.\n" +
                        "Add income and expense details manually."
            )

            ExpandableSection(
                "Managing Expenses",
                "Track daily income, expenses, and savings.\n" +
                        "View spending patterns through charts.\n" +
                        "Receive alerts before exceeding limits."
            )

            ExpandableSection(
                "Investment Guidance",
                "Explore smart investing options.\n" +
                        "Available only for users aged 18+."
            )

            ExpandableSection(
                "Account & Security",
                "Update personal details anytime.\n" +
                        "Keep login credentials secure.\n" +
                        "Logout from shared devices."
            )

            ExpandableSection(
                "Troubleshooting",
                "Restart or update the app.\n" +
                        "Check internet connection.\n" +
                        "Contact support if issue continues."
            )

            ExpandableSection(
                "Contact Support",
                "Email: support@yourappname.com\n" +
                        "In-App Support: 24/7 Available\n\n" +
                        "We’re here to help you manage finances smarter."
            )
        }
    }
}

@Composable
fun ExpandableSection(
    title: String,
    content: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(title, style = MaterialTheme.typography.titleMedium)

                Icon(
                    imageVector =
                        if (expanded)
                            Icons.Default.ExpandLess
                        else
                            Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(content, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}