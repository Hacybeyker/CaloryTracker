package com.hacybeyker.core.util

sealed class UiEvent {
    data class Navigate(var route: String) : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackbar(val message: UiText) : UiEvent()

}