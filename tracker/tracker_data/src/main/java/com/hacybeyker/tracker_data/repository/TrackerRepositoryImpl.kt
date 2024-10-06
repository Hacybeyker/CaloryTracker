package com.hacybeyker.tracker_data.repository

import com.hacybeyker.tracker_data.local.TrackerDao
import com.hacybeyker.tracker_data.mapper.toTrackableFood
import com.hacybeyker.tracker_data.mapper.toTrackedFood
import com.hacybeyker.tracker_data.mapper.toTrackedFoodEntity
import com.hacybeyker.tracker_data.remote.OpenFoodApi
import com.hacybeyker.tracker_domain.model.TrackableFood
import com.hacybeyker.tracker_domain.model.TrackedFood
import com.hacybeyker.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi,
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>> =
        try {
            val searchDto =
                api.searchFood(
                    query = query,
                    page = page,
                    pageSize = pageSize,
                )
            Result.success(
                searchDto.products
                    .filter {
                        val calculatorCalories =
                            it.nutriments.carbohydrates100g * 4f +
                                    it.nutriments.proteins100g * 4f +
                                    it.nutriments.fat100g * 9f
                        val lowerBound = calculatorCalories * 0.99f
                        val upperBound = calculatorCalories * 1.01f
                        it.nutriments.energyKcal100g in (lowerBound..upperBound)
                    }.mapNotNull { it.toTrackableFood() },
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> =
        dao
            .getFoodsForDate(
                day = localDate.dayOfMonth,
                month = localDate.monthValue,
                year = localDate.year,
            ).map { entities ->
                entities.map { it.toTrackedFood() }
            }
}
