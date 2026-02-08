package ru.sicampus.bootcamp2026.ui.screens.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2026.domain.entities.MeetingCreate
import ru.sicampus.bootcamp2026.domain.usecase.meeting.CreateMeetingUseCase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookViewModel(
    private val createMeetingUseCase: CreateMeetingUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookUiState())
    val state: StateFlow<BookUiState> = _state.asStateFlow()

    fun onTitleChange(title: String) {
        _state.update { it.copy(title = title, errorMessage = null) }
    }

    fun onDescriptionChange(description: String) {
        _state.update { it.copy(description = description, errorMessage = null) }
    }

    fun onDateChange(date: LocalDate) {
        _state.update { it.copy(selectedDate = date, errorMessage = null) }
    }

    fun onStartTimeChange(time: LocalTime) {
        _state.update { it.copy(selectedStartTime = time, errorMessage = null) }
    }

    fun onEndTimeChange(time: LocalTime) {
        _state.update { it.copy(selectedEndTime = time, errorMessage = null) }
    }

    fun onCabinetChange(cabinet: String) {
        _state.update { it.copy(cabinet = cabinet, errorMessage = null) }
    }

    fun addParticipant(userId: Long) {
        if (!_state.value.selectedParticipants.contains(userId)) {
            _state.update {
                it.copy(selectedParticipants = _state.value.selectedParticipants + userId)
            }

        }
    }

    fun removeParticipant(userId: Long) {
        _state.update {
            it.copy(selectedParticipants = _state.value.selectedParticipants.filter { it != userId })
        }
    }

    fun createMeeting(organizerId: Long) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            val meetingData = MeetingCreate(
                title = _state.value.title,
                description = _state.value.description,
                address = _state.value.cabinet,
                date = _state.value.selectedDate.toString(),
                timeStart = LocalDateTime.of(_state.value.selectedDate, _state.value.selectedStartTime),
                timeEnd = LocalDateTime.of(_state.value.selectedDate, _state.value.selectedEndTime),
                organizerId = organizerId,
                participantIds = _state.value.selectedParticipants
            )

            val result = createMeetingUseCase(meetingData)

            result.fold(
                onSuccess = {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            title = "",
                            description = "",
                            selectedParticipants = emptyList(),
                            cabinet = "Не выбрано"
                        )
                    }
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Ошибка создания встречи"
                        )
                    }
                }
            )
        }
    }

    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }

    fun clearSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}