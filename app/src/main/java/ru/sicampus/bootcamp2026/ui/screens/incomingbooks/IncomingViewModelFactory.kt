package ru.sicampus.bootcamp2026.ui.screens.incomingbooks

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.sicampus.bootcamp2026.data.repository.MeetingRepository
import ru.sicampus.bootcamp2026.data.source.AuthLocalDataSource
import ru.sicampus.bootcamp2026.data.source.MeetingDataSource
import ru.sicampus.bootcamp2026.domain.usecase.invitation.GetInvitationsUseCase
import ru.sicampus.bootcamp2026.domain.usecase.invitation.RespondToInvitationUseCase

object IncomingViewModelFactory {
    fun create(): ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val authLocalDataSource = AuthLocalDataSource
            val meetingDataSource = MeetingDataSource()
            val meetingRepository = MeetingRepository(meetingDataSource, authLocalDataSource)
            val getInvitationsUseCase = GetInvitationsUseCase(meetingRepository)
            val respondToInvitationUseCase = RespondToInvitationUseCase(meetingRepository)

            IncomingViewModel(getInvitationsUseCase, respondToInvitationUseCase)
        }
    }
}