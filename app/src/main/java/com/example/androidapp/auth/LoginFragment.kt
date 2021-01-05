package com.example.androidapp.auth

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.androidapp.R
import com.example.androidapp.domain.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.login_fragment.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setupLoginForm()
    }

    private fun setupLoginForm() {
        viewModel.loginFormState.observe(viewLifecycleOwner) { loginState ->
            login_button.isEnabled = loginState.isDataValid
        }
        viewModel.loginResult.observe(viewLifecycleOwner) { loginResult ->
            loading.visibility = View.GONE
            if (loginResult is Result.Success<*>) {
                findNavController().navigate(R.id.FirstFragment)
            } else if (loginResult is Result.Error) {
                error_text.text = "Invalid credentials"
                error_text.visibility = View.VISIBLE
            }
        }
        username_input.afterTextChanged {
            viewModel.loginDataChanged(
                username_input.text.toString(),
                password_input.text.toString()
            )
        }
        password_input.afterTextChanged {
            viewModel.loginDataChanged(
                username_input.text.toString(),
                password_input.text.toString()
            )
        }
        login_button.setOnClickListener {
            loading.visibility = View.VISIBLE
            error_text.visibility = View.GONE
            viewModel.login(username_input.text.toString(), password_input.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}