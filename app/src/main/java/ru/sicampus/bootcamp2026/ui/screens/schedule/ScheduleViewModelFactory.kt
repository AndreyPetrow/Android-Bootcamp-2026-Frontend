package ru.sicampus.bootcamp2026.ui.screens.schedule

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.sicampus.bootcamp2026.data.repository.MeetingRepository
import ru.sicampus.bootcamp2026.data.source.MeetingDataSource
import ru.sicampus.bootcamp2026.domain.usecase.ViewScheduleUseCase

object ScheduleViewModelFactory {
    fun create(): ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val meetingDataSource = MeetingDataSource()
            val meetingRepository = MeetingRepository(meetingDataSource)
            val viewScheduleUseCase = ViewScheduleUseCase(meetingRepository)

            ScheduleViewModel(viewScheduleUseCase)
        }
    }
}