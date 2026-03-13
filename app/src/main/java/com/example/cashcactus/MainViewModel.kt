package com.example.cashcactus

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch
import java.util.Calendar

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "cash_database"
    ).fallbackToDestructiveMigration().build()

    private val dao = db.appDao()

    // USER
    var currentUserId by mutableStateOf(0)
        private set

    var currentUserName by mutableStateOf("")
        private set

    var currentUserEmail by mutableStateOf("")
        private set

    var currentUserPassword by mutableStateOf("")
        private set

    // DASHBOARD
    var dashboardCategory by mutableStateOf("")
    var dashboardAge by mutableStateOf(0)

    var monthlySalary by mutableStateOf(0f)
    var savingType by mutableStateOf("None")
    var savingValue by mutableStateOf(0f)

    // EXPENSE VALUES (for graph)
    var food by mutableStateOf(0f)
    var rent by mutableStateOf(0f)
    var medical by mutableStateOf(0f)
    var emi by mutableStateOf(0f)
    var additional by mutableStateOf(0f)
    var education by mutableStateOf(0f)

    var expenseList by mutableStateOf<List<Expense>>(emptyList())
        private set

    private fun setCurrentUser(user: User) {
        currentUserId = user.id
        currentUserName = user.name
        currentUserEmail = user.email
        currentUserPassword = user.password
    }

    // REGISTER
    fun register(name: String, email: String, password: String, onResult: (Boolean) -> Unit) {

        viewModelScope.launch {

            val existingUser = dao.getUserByEmail(email)

            if (existingUser != null) {
                onResult(false)
                return@launch
            }

            val user = User(
                name = name,
                email = email,
                password = password
            )

            dao.insertUser(user)

            val loggedUser = dao.login(email, password)

            loggedUser?.let {
                setCurrentUser(it)
                onResult(true)
            } ?: onResult(false)
        }
    }

    // LOGIN
    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {

        viewModelScope.launch {

            val user = dao.login(email, password)

            if (user != null) {
                setCurrentUser(user)
                loadDashboardData()
                loadExpenses()
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    // UPDATE USER
    fun updateUser(name: String, email: String, password: String, oldEmail: String) {

        viewModelScope.launch {

            dao.updateUser(name, email, password, oldEmail)

            val updatedUser = dao.login(email, password)

            updatedUser?.let {
                setCurrentUser(it)
            }
        }
    }

    // SAVE DASHBOARD
    fun saveDashboardData() {

        viewModelScope.launch {

            if (currentUserId == 0) return@launch

            dao.insertDashboard(
                DashboardData(
                    userId = currentUserId,
                    category = dashboardCategory,
                    age = dashboardAge,
                    salary = monthlySalary,
                    savingType = savingType,
                    savingValue = savingValue
                )
            )
        }
    }

    // LOAD DASHBOARD
    fun loadDashboardData() {

        viewModelScope.launch {

            val data = dao.getDashboard(currentUserId)

            data?.let {
                dashboardCategory = it.category
                dashboardAge = it.age
                monthlySalary = it.salary
                savingType = it.savingType
                savingValue = it.savingValue
            }
        }
    }

    // SAVE MONTHLY EXPENSES
    fun saveMonthlyExpenses() {

        viewModelScope.launch {

            val categories = listOf(
                "Food" to food,
                "Rent" to rent,
                "Medical" to medical,
                "EMI" to emi,
                "Additional" to additional,
                "Education" to education
            )

            categories.forEach {

                if (it.second > 0f) {

                    dao.insertExpense(
                        Expense(
                            title = it.first,
                            amount = it.second.toDouble(),
                            userId = currentUserId
                        )
                    )
                }
            }

            loadExpenses()
        }
    }

    // LOAD ALL EXPENSES
    fun loadExpenses() {

        viewModelScope.launch {

            expenseList = dao.getExpenses(currentUserId)

            updateCategoryValues()
        }
    }

    // UPDATE CATEGORY VALUES FROM DATABASE
    private fun updateCategoryValues() {

        food = expenseList.find { it.title == "Food" }?.amount?.toFloat() ?: 0f
        rent = expenseList.find { it.title == "Rent" }?.amount?.toFloat() ?: 0f
        medical = expenseList.find { it.title == "Medical" }?.amount?.toFloat() ?: 0f
        emi = expenseList.find { it.title == "EMI" }?.amount?.toFloat() ?: 0f
        additional = expenseList.find { it.title == "Additional" }?.amount?.toFloat() ?: 0f
        education = expenseList.find { it.title == "Education" }?.amount?.toFloat() ?: 0f
    }

    // DAILY EXPENSES
    fun loadDailyExpenses() {

        viewModelScope.launch {

            val cal = Calendar.getInstance()

            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)

            val start = cal.timeInMillis

            expenseList = dao.getDailyExpenses(currentUserId, start)

            updateCategoryValues()
        }
    }

    // WEEKLY EXPENSES
    fun loadWeeklyExpenses() {

        viewModelScope.launch {

            val cal = Calendar.getInstance()

            cal.add(Calendar.DAY_OF_YEAR, -7)

            val start = cal.timeInMillis

            expenseList = dao.getWeeklyExpenses(currentUserId, start)

            updateCategoryValues()
        }
    }

    // MONTHLY EXPENSES
    fun loadMonthlyExpenses() {

        viewModelScope.launch {

            val cal = Calendar.getInstance()

            cal.set(Calendar.DAY_OF_MONTH, 1)

            val start = cal.timeInMillis

            expenseList = dao.getMonthlyExpenses(currentUserId, start)

            updateCategoryValues()
        }
    }

    // AI ADVISOR
    fun getAIAdvice(): String {

        val total =
            food + rent + medical + emi + additional + education

        val savings = monthlySalary - total

        return when {

            total > monthlySalary ->
                "⚠ You exceeded your monthly budget"

            total > monthlySalary * 0.9 ->
                "⚠ You are close to your monthly limit"

            food > monthlySalary * 0.3 ->
                "You spend too much on food"

            savings > monthlySalary * 0.3 ->
                "Great savings! Consider investing in mutual funds"

            savings > monthlySalary * 0.15 ->
                "You can start SIP investments"

            else ->
                "Try reducing expenses to build savings"
        }
    }
}