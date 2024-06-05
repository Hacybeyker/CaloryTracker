package com.hacybeyker.calorytracker.navigation

import androidx.navigation.NavController
import com.hacybeyker.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}