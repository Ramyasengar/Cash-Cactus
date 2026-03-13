package com.example.cashcactus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Cash Cactus") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            Text(
                text = "🌵 Smart. Secure. Simplified.",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Cash Cactus is a next-generation personal finance solution engineered to simplify and elevate money management for everyone—from housewives and students to families and working professionals.",
                style = MaterialTheme.typography.bodyLarge
            )

            Divider()

            SectionHeading("📊 Intelligent Financial Tracking")

            Text(
                text = "Powered by intelligent analytics and secure banking API integrations, the app automatically tracks income, expenses, and savings while transforming financial data into interactive visual charts for instant clarity.",
                style = MaterialTheme.typography.bodyMedium
            )

            SectionHeading("🚨 Smart Spending Alerts")

            Text(
                text = "The app proactively monitors spending patterns and instantly notifies users when they are about to exceed their monthly expenditure limits, helping prevent overspending before it happens.",
                style = MaterialTheme.typography.bodyMedium
            )

            SectionHeading("📈 Guided Investment Insights")

            Text(
                text = "Cash Cactus offers guided insights and recommendations that lead users toward smarter investing options, enabling sustainable long-term financial growth.",
                style = MaterialTheme.typography.bodyMedium
            )

            SectionHeading("🔐 Security & Trust")

            Text(
                text = "With a seamless blend of simplicity, automation, and data-driven intelligence, the app empowers users to take confident control of their finances while ensuring top-tier security standards.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Build a smarter and more secure financial future with Cash Cactus.",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun SectionHeading(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )
}