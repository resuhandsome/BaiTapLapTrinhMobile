package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BackgroundViewModel(
    private val repository: BackgroundPreferencesRepository
) : ViewModel() {

    private val _backgroundColor = MutableStateFlow(BackgroundPreferencesRepository.WHITE)
    val backgroundColor: StateFlow<String> = _backgroundColor.asStateFlow()

    private val _selectedColor = MutableStateFlow(BackgroundPreferencesRepository.WHITE)
    val selectedColor: StateFlow<String> = _selectedColor.asStateFlow()

    init {
        viewModelScope.launch {
            repository.backgroundColorFlow.collect { color ->
                _backgroundColor.value = color
                _selectedColor.value = color
            }
        }
    }

    fun selectColor(color: String) {
        _selectedColor.value = color
    }

    fun saveBackgroundColor() {
        viewModelScope.launch {
            repository.saveBackgroundColor(_selectedColor.value)
        }
    }
}
