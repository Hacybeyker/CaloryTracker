package com.hacybeyker.tracker_domain.use_case

import com.hacybeyker.tracker_domain.model.TrackedFood
import com.hacybeyker.tracker_domain.repository.TrackerRepository

class DeleteTrackedFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        trackedFood: TrackedFood
    ) {
        repository.deleteTrackedFood(food = trackedFood)
    }

}