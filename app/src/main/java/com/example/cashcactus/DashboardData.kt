package com.example.cashcactus

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DashboardData(

    @PrimaryKey
    val userId: Int,

    val category: String,
    val age: Int,
    val salary: Float,
    val savingType: String,
    val savingValue: Float
)