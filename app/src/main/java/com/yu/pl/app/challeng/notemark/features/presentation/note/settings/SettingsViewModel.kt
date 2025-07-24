package com.yu.pl.app.challeng.notemark.features.presentation.note.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.network.domain.ConnectivityRepository
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository
import com.yu.pl.app.challeng.notemark.core.ui.util.DateFormat
import com.yu.pl.app.challeng.notemark.core.ui.util.UiText
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.ChangeSyncIntervalUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.LogoutUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.LogoutUseCaseResult
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.SyncDataUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.SyncLogoutUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.UnsyncLogoutUseCase
import com.yu.pl.app.challeng.notemark.features.presentation.models.SyncIntervalUI
import com.yu.pl.app.challeng.notemark.features.presentation.models.toSyncInterval
import com.yu.pl.app.challeng.notemark.features.presentation.models.toSyncIntervalUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class SettingsViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val unsyncLogoutUseCase: UnsyncLogoutUseCase,
    private val syncLogoutUseCase: SyncLogoutUseCase,
    private val syncDataUseCase: SyncDataUseCase,
    private val changeSyncIntervalUseCase: ChangeSyncIntervalUseCase,
    private val connectivityRepository: ConnectivityRepository,
    private val preferenceRepository: PreferenceRepository,
) : ViewModel() {

    private var isInitialized = false

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.onStart {
        if (!isInitialized) {
            observeSyncDateTime()
            observeSyncInterval()
            observeConnection()
            isInitialized = true
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        SettingsState()
    )

    private val _event = Channel<SettingsEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.OnLogout -> logout()
            SettingsAction.OnBackNavigation -> Unit
            is SettingsAction.OnChangeSyncInterval -> changeSyncInterval(action.interval)
            SettingsAction.OnSyncData -> syncData()
            SettingsAction.OnSyncLogout -> syncLogout()
            SettingsAction.OnUnSyncLogout -> unSyncLogout()
            SettingsAction.OnCancelSync -> cancelSync()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            if (!_state.value.hasNetworkConnection) {
                _event.send(SettingsEvent.ShowToastMessage(UiText.ResourceString(R.string.logout_need_internet_message)))
                return@launch
            }

            _state.update {
                it.copy(isLoading = true)
            }

            val result = logoutUseCase()

            when (result) {
                LogoutUseCaseResult.Error -> _event.send(
                    SettingsEvent.ShowToastMessage(
                        UiText.ResourceString(R.string.settings_logout_fail)
                    )
                )

                LogoutUseCaseResult.RemainSyncQueue -> {
                    _state.update {
                        it.copy(isShowConfirmSyncInLogoutDialog = true)
                    }
                }

                LogoutUseCaseResult.Success -> {
                    _event.send(SettingsEvent.NavigateLogin)
                }
            }
        }.invokeOnCompletion {
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun unSyncLogout() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isShowConfirmSyncInLogoutDialog = false,
                    isShowSyncFailDialog = false
                )
            }
            if (unsyncLogoutUseCase().isFailure) {
                _event.send(SettingsEvent.ShowToastMessage(UiText.ResourceString(R.string.settings_logout_fail)))
                return@launch
            }
            _event.send(SettingsEvent.NavigateLogin)

        }.invokeOnCompletion {
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun syncLogout() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isShowConfirmSyncInLogoutDialog = false,
                    isShowSyncFailDialog = false
                )
            }

            if (syncLogoutUseCase().isFailure) {
                _state.update {
                    it.copy(
                        isShowConfirmSyncInLogoutDialog = false,
                        isShowSyncFailDialog = true
                    )
                }
                return@launch
            }
            _event.send(SettingsEvent.NavigateLogin)
        }.invokeOnCompletion {
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun syncData() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val toastMessage = if(syncDataUseCase().isSuccess){
                UiText.ResourceString(R.string.settings_sync_success)
            }else{
                UiText.ResourceString(R.string.settings_sync_fail)
            }
            _event.send(SettingsEvent.ShowToastMessage(toastMessage))

        }.invokeOnCompletion {
            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun cancelSync() {
        _state.update {
            it.copy(
                isLoading = false,
                isShowConfirmSyncInLogoutDialog = false,
                isShowSyncFailDialog = false
            )
        }
    }

    private fun changeSyncInterval(interval: SyncIntervalUI) {
        viewModelScope.launch {
            changeSyncIntervalUseCase(interval.toSyncInterval())
        }
    }

    private fun observeSyncDateTime() {
        viewModelScope.launch {
            preferenceRepository.getSyncTimestamp.map { dateTime ->
                if (dateTime == null) return@map UiText.ResourceString(R.string.last_sync_date_never)

                createSyncDateString(dateTime.toEpochSecond())

            }.collect { timeText ->
                _state.update {
                    it.copy(lastSyncDateStr = timeText)
                }
            }
        }
    }

    private fun observeSyncInterval() {
        viewModelScope.launch {
            preferenceRepository.getSyncInterval.map { interval ->
                interval.toSyncIntervalUi()
            }.collect { intervalUi ->
                _state.update {
                    it.copy(syncInterval = intervalUi)
                }
            }
        }
    }

    private fun observeConnection() {
        viewModelScope.launch {
            connectivityRepository.internetConnection.collect { connection ->
                _state.update {
                    it.copy(hasNetworkConnection = connection)
                }
            }
        }
    }

    private fun createSyncDateString(epochSecond: Long): UiText {
        val elapsedTime =
            (System.currentTimeMillis() / 1000 - epochSecond).toDuration(DurationUnit.SECONDS)

        if (elapsedTime < 5.minutes) return UiText.ResourceString(R.string.last_sync_just_now)
        if (elapsedTime < 1.hours) return UiText.ResourceString(
            R.string.last_sync_time_ago,
            "${elapsedTime.inWholeMinutes} minutes"
        )
        if (elapsedTime < 2.hours) return UiText.ResourceString(
            R.string.last_sync_time_ago,
            "${elapsedTime.inWholeHours} hour"
        )
        if (elapsedTime < 1.days) return UiText.ResourceString(
            R.string.last_sync_time_ago,
            "${elapsedTime.inWholeHours} hours"
        )
        if (elapsedTime < 2.days) return UiText.ResourceString(
            R.string.last_sync_time_ago,
            "${elapsedTime.inWholeDays} day"
        )
        if (elapsedTime < 8.days) return UiText.ResourceString(
            R.string.last_sync_time_ago,
            "${elapsedTime.inWholeDays} days"
        )

        val format = DateFormat.yearToSecond(Locale.getDefault())
        return UiText.PlainString(format.format(Date(epochSecond * 1000)))
    }
}