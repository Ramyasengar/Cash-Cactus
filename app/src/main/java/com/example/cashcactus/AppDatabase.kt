package com.example.cashcactus

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        User::class,
        Expense::class,
        DashboardData::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}