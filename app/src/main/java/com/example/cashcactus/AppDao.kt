package com.example.cashcactus

import androidx.room.*

@Dao
interface AppDao {

    // ---------- USER ----------

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("""
        UPDATE users 
        SET name = :name,
            email = :email,
            password = :password
        WHERE email = :oldEmail
    """)
    suspend fun updateUser(
        name: String,
        email: String,
        password: String,
        oldEmail: String
    )

    // ---------- EXPENSE ----------

    @Insert
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM expenses WHERE userId = :userId ORDER BY date DESC")
    suspend fun getExpenses(userId: Int): List<Expense>

    @Query("SELECT * FROM expenses WHERE userId = :userId AND date >= :startOfDay")
    suspend fun getDailyExpenses(userId: Int, startOfDay: Long): List<Expense>

    @Query("SELECT * FROM expenses WHERE userId = :userId AND date >= :startOfWeek")
    suspend fun getWeeklyExpenses(userId: Int, startOfWeek: Long): List<Expense>

    @Query("SELECT * FROM expenses WHERE userId = :userId AND date >= :startOfMonth")
    suspend fun getMonthlyExpenses(userId: Int, startOfMonth: Long): List<Expense>


    // ---------- DASHBOARD ----------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDashboard(data: DashboardData)

    @Query("SELECT * FROM DashboardData WHERE userId = :userId LIMIT 1")
    suspend fun getDashboard(userId: Int): DashboardData?
}