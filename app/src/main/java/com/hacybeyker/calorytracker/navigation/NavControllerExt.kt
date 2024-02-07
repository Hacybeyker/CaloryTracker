package com.hacybeyker.calorytracker.navigation

import androidx.navigation.NavController
import com.hacybeyker.core.util.UIEvent

fun NavController.navigate(event: UIEvent.Navigate) {
    this.navigate(event.route)
}