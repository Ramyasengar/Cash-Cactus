package com.example.cashcactus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            SectionTitle("🔐 Your Privacy Matters")

            BodyText("Your privacy is our top priority. This Privacy Policy explains how our app collects, uses, stores, and protects your personal and financial information.")

            SectionTitle("1️⃣ Information We Collect")

            BulletPoint("Personal details such as name, email address, and contact information.")
            BulletPoint("Financial data including income, expenses, and savings entered by the user.")
            BulletPoint("Transaction data retrieved securely through banking API integrations (with user consent).")
            BulletPoint("App usage data to improve performance and user experience.")

            SectionTitle("2️⃣ How We Use Your Information")

            BulletPoint("Track and analyze income, expenses, and savings.")
            BulletPoint("Generate visual charts and financial insights.")
            BulletPoint("Monitor spending limits and send alerts.")
            BulletPoint("Provide guidance and smart investment recommendations.")
            BulletPoint("Improve app features, security, and overall functionality.")

            SectionTitle("3️⃣ Data Security")

            BodyText("We implement industry-standard security measures to protect your data. All sensitive data is encrypted and processed through secure systems to ensure confidentiality and integrity.")

            SectionTitle("4️⃣ Data Sharing")

            BulletPoint("We do NOT sell, trade, or rent your personal data.")
            BulletPoint("Shared only with trusted service providers for functionality.")
            BulletPoint("Shared when required by law.")

            SectionTitle("5️⃣ User Control & Consent")

            BulletPoint("Review, update, or delete your information.")
            BulletPoint("Enable or disable banking integrations anytime.")
            BulletPoint("Withdraw consent for data processing.")

            SectionTitle("6️⃣ Cookies & Analytics")

            BodyText("We may use analytics tools to improve user experience. These tools do not collect personally identifiable information without consent.")

            SectionTitle("7️⃣ Children’s Privacy")

            BodyText("Our app is not intended for users under the age of 18. We do not knowingly collect data from minors.")

            SectionTitle("8️⃣ Policy Updates")

            BodyText("We may update this policy from time to time. Continued use of the app implies acceptance of updated terms.")
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun BodyText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun BulletPoint(text: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text("• ")
        Text(text, modifier = Modifier.weight(1f))
    }
}