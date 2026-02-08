package ru.sicampus.bootcamp2026.ui.screens.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        _state.value = _state.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun onDateChange(date: LocalDate) {
        _state.value = _state.value.copy(selectedDate = date)
    }

    fun onStartTimeChange(time: LocalTime) {
        _state.value = _state.value.copy(selectedStartTime = time)
    }

    fun onEndTimeChange(time: LocalTime) {
        _state.value = _state.value.copy(selectedEndTime = time)
    }

    fun onCabinetChange(cabinet: String) {
        _state.value = _state.value.copy(cabinet = cabinet)
    }

    fun addParticipant(userId: Long) {
        if (!_state.value.selectedParticipants.contains(userId)) {
            _state.value = _state.value.copy(
                selectedParticipants = _state.value.selectedParticipants + userId
            )
        }
    }

    fun removeParticipant(userId: Long) {
        _state.value = _state.value.copy(
            selectedParticipants = _state.value.selectedParticipants.filter { it != userId }
        )
    }

    fun createMeeting(organizerId: Long) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

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
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        title = "",
                        description = "",
                        selectedParticipants = emptyList(),
                        cabinet = "Не выбрано"
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Ошибка создания встречи"
                    )
                }
            )
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    fun clearSuccess() {
        _state.value = _state.value.copy(isSuccess = false)
    }
}