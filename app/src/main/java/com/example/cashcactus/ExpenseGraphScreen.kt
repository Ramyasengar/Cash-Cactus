package com.example.cashcactus

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import android.graphics.Paint
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ExpenseGraphScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {

    val salary = viewModel.monthlySalary
    val savingType = viewModel.savingType
    val savingValue = viewModel.savingValue

    val food = viewModel.food
    val rent = viewModel.rent
    val medical = viewModel.medical
    val emi = viewModel.emi
    val additional = viewModel.additional
    val education = viewModel.education

    var graphType by remember { mutableStateOf("Monthly") }

    // Savings calculation
    val savingsAmount = when (savingType) {
        "Percentage" -> (salary * savingValue) / 100f
        "Amount" -> savingValue
        else -> 0f
    }

    val totalExpenses =
        food + rent + medical + emi + additional + education + savingsAmount

    val remaining = salary - totalExpenses

    val exceededAmount =
        if (totalExpenses > salary) totalExpenses - salary else 0f

    // Graph data
    val categories = mutableListOf(
        "Food" to food,
        "Rent" to rent,
        "Medical" to medical,
        "Additional" to additional,
        "Education" to education
    )

    if (emi > 0f) categories.add("EMI" to emi)
    if (savingsAmount > 0f) categories.add("Savings" to savingsAmount)
    if (remaining > 0f) categories.add("Remaining" to remaining)

    val maxValue = categories.maxOfOrNull { it.second } ?: 1f

    // Animation
    val animatedValues = categories.map { pair ->

        val rawValue = when (graphType) {
            "Daily" -> pair.second / 30f
            "Weekly" -> pair.second / 4f
            else -> pair.second
        }

        animateFloatAsState(
            targetValue = rawValue,
            animationSpec = tween(800),
            label = ""
        ).value
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "$graphType Expense Analysis",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // GRAPH CARD
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .padding(16.dp)
            ) {

                val barWidth = size.width / (categories.size * 2)

                val textPaint = Paint().apply {
                    textAlign = Paint.Align.CENTER
                    textSize = 28f
                    color = android.graphics.Color.BLACK
                }

                categories.forEachIndexed { index, pair ->

                    val value = animatedValues[index]

                    val barHeight =
                        (value / maxValue) * (size.height - 100)

                    val xPosition = index * barWidth * 2 + barWidth

                    drawRoundRect(
                        color = Color(0xFF7E57C2),
                        topLeft = Offset(
                            xPosition,
                            size.height - barHeight - 60
                        ),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(25f, 25f)
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        pair.first,
                        xPosition + barWidth / 2,
                        size.height - 20,
                        textPaint
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        value.toInt().toString(),
                        xPosition + barWidth / 2,
                        size.height - barHeight - 75,
                        textPaint
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // GRAPH TOGGLE BUTTONS
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                onClick = { graphType = "Daily" },
                modifier = Modifier.weight(1f)
            ) {
                Text("Daily")
            }

            Button(
                onClick = { graphType = "Weekly" },
                modifier = Modifier.weight(1f)
            ) {
                Text("Weekly")
            }

            Button(
                onClick = { graphType = "Monthly" },
                modifier = Modifier.weight(1f)
            ) {
                Text("Monthly")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (exceededAmount > 0f) {

            Text(
                text = "⚠ Your expenses exceeded salary by ₹${exceededAmount.toInt()}",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}