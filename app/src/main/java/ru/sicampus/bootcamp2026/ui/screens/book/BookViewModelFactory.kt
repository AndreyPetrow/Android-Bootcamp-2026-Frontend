package ru.sicampus.bootcamp2026.ui.screens.book

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.sicampus.bootcamp2026.data.repository.MeetingRepository
import ru.sicampus.bootcamp2026.data.source.MeetingDataSource
import ru.sicampus.bootcamp2026.domain.usecase.meeting.CreateMeetingUseCase

object BookViewModelFactory {
    fun create(context: Context): ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val meetingDataSource = MeetingDataSource()
            val meetingRepository = MeetingRepository(meetingDataSource)
            val createMeetingUseCase = CreateMeetingUseCase(meetingRepository)

            BookViewModel(createMeetingUseCase)
        }
    }
}