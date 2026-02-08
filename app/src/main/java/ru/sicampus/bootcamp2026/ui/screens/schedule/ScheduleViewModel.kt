package ru.sicampus.bootcamp2026.ui.screens.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2026.domain.entities.MeetingMini
import ru.sicampus.bootcamp2026.domain.usecase.ViewScheduleUseCase
import java.time.LocalDate
import java.time.temporal.IsoFields

enum class Period {
    DAY, WEEK, MONTH
}

class ScheduleViewModel(
    private val viewScheduleUseCase: ViewScheduleUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ScheduleUiState())
    val state: StateFlow<ScheduleUiState> = _state.asStateFlow()

    init {
        loadSchedule()
    }

    fun selectPeriod(period: Period) {
        _state.update { it.copy(selectedPeriod = period) }
        loadSchedule()
    }

    fun selectDate(date: LocalDate) {
        _state.update { it.copy(selectedDate = date) }
        loadSchedule()
    }

    fun refresh() {
        loadSchedule()
    }

    private fun loadSchedule() {
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                when (_state.value.selectedPeriod) {
                    Period.DAY -> loadDaySchedule()
                    Period.WEEK -> loadWeekSchedule()
                    Period.MONTH -> loadMonthSchedule()
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: "Ошибка загрузки расписания") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun loadDaySchedule() {
        val result = viewScheduleUseCase.getDaySchedule(_state.value.selectedDate)
        result.fold(
            onSuccess = { meetings ->
                _state.update { it.copy(dayMeetings = meetings) }
            },
            onFailure = { error ->
                throw error
            }
        )
    }

    private suspend fun loadWeekSchedule() {
        val year = _state.value.selectedDate.year
        val week = _state.value.selectedDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
        val result = viewScheduleUseCase.getWeekSchedule(year, week)
        result.fold(
            onSuccess = { meetings ->
                _state.update { it.copy(weekMeetings = meetings) }
            },
            onFailure = { error ->
                throw error
            }
        )
    }

    private suspend fun loadMonthSchedule() {
        val year = _state.value.selectedDate.year
        val month = _state.value.selectedDate.monthValue
        val result = viewScheduleUseCase.getMonthSchedule(year, month)
        result.fold(
            onSuccess = { meetings ->
                _state.update { it.copy(monthMeetings = meetings) }
            },
            onFailure = { error ->
                throw error
            }
        )
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    fun getMeetingsForDate(date: LocalDate): List<MeetingMini> {
        return when (_state.value.selectedPeriod) {
            Period.DAY -> if (date == _state.value.selectedDate) _state.value.dayMeetings else emptyList()
            Period.WEEK -> _state.value.weekMeetings[date] ?: emptyList()
            Period.MONTH -> _state.value.monthMeetings[date] ?: emptyList()
        }
    }

    fun hasMeetingsForDate(date: LocalDate): Boolean {
        return when (_state.value.selectedPeriod) {
            Period.DAY -> date == _state.value.selectedDate && _state.value.dayMeetings.isNotEmpty()
            Period.WEEK -> _state.value.weekMeetings[date]?.isNotEmpty() ?: false
            Period.MONTH -> _state.value.monthMeetings[date]?.isNotEmpty() ?: false
        }
    }
}