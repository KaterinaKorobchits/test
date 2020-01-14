package my.luckydog.presentation.fragments.signup.viewmodel

import androidx.databinding.ObservableField

data class SignUpForm(
    val email: ObservableField<String> = ObservableField(),
    val password: ObservableField<String> = ObservableField(),
    val emailError: ObservableField<String> = ObservableField(),
    val passwordError: ObservableField<String> = ObservableField()
) {
    internal fun hideErrors() {
        emailError.set(null)
        passwordError.set(null)
    }
}