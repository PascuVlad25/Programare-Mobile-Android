package com.example.androidapp.auth

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.androidapp.domain.Result

class LoginViewModel : ViewModel() {

    private val mutableLoginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = mutableLoginFormState

    private val mutableLoginResult = MutableLiveData<Result<TokenHolder>>()
    val loginResult: LiveData<Result<TokenHolder>> = mutableLoginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            mutableLoginResult.value = AuthRepository.login(username, password)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (isUserNameValid(username) && isPasswordValid(password)) {
            mutableLoginFormState.value = LoginFormState(isDataValid = true)
        } else {
            mutableLoginFormState.value = LoginFormState(isDataValid = false)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank();
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 1;
    }
}