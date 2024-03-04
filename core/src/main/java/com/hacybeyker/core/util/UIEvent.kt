package com.hacybeyker.core.util

sealed class UIEvent {
    data class Navigate(var route: String) : UIEvent()
    object NavigateUp : UIEvent()
    data class ShowSnackbar(val message: UiText) : UIEvent()

}