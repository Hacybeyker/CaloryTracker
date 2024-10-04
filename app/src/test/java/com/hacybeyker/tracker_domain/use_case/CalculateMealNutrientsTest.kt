package com.hacybeyker.tracker_domain.use_case

import com.google.common.truth.Truth.assertThat
import com.hacybeyker.core.domain.model.ActivityLevel
import com.hacybeyker.core.domain.model.Gender
import com.hacybeyker.core.domain.model.GoalType
import com.hacybeyker.core.domain.model.UserInfo
import com.hacybeyker.core.domain.preferences.Preferences
import com.hacybeyker.tracker_domain.model.MealType
import com.hacybeyker.tracker_domain.model.TrackedFood
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsTest {

    private lateinit var sutCalculateMealNutrients: CalculateMealNutrients

    @Before
    fun setup() {
        val preferences = mockk<Preferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 80f,
            height = 180,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f
        )
        sutCalculateMealNutrients = CalculateMealNutrients(preferences = preferences)
    }

    @Test
    fun `Calories for breakfast properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                imageUrl = null,
                mealType = MealType.fromString(
                    listOf(
                        "breakfast",
                        "lunch",
                        "dinner",
                        "snack"
                    ).random()
                ),
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(100)
            )
        }
        val result = sutCalculateMealNutrients(trackedFoods = trackedFoods)

        val breakfastCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        val expectedCalories = trackedFoods
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedCalories)
    }

    @Test
    fun `Carbs for dinner properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                imageUrl = null,
                mealType = MealType.fromString(
                    listOf(
                        "breakfast",
                        "lunch",
                        "dinner",
                        "snack"
                    ).random()
                ),
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(100)
            )
        }
        val result = sutCalculateMealNutrients(trackedFoods = trackedFoods)

        val breakfastCarbs = result.mealNutrients.values
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.carbs }

        val expectedCarbs = trackedFoods
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.carbs }

        assertThat(breakfastCarbs).isEqualTo(expectedCarbs)
    }
}