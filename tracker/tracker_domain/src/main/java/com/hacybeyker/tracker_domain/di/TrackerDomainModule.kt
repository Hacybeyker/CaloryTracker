package com.hacybeyker.tracker_domain.di

import com.hacybeyker.core.domain.preferences.Preferences
import com.hacybeyker.tracker_domain.repository.TrackerRepository
import com.hacybeyker.tracker_domain.use_case.CalculateMealNutrients
import com.hacybeyker.tracker_domain.use_case.DeleteTrackedFood
import com.hacybeyker.tracker_domain.use_case.GetFoodsForDate
import com.hacybeyker.tracker_domain.use_case.SearchFood
import com.hacybeyker.tracker_domain.use_case.TrackFood
import com.hacybeyker.tracker_domain.use_case.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCase(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCases {
        return com.hacybeyker.tracker_domain.use_case.TrackerUseCases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences = preferences)
        )
    }
}