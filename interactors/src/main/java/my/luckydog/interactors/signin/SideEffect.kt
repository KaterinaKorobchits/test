package my.luckydog.interactors.signin

sealed class SideEffect {

    sealed class Navigate : SideEffect() {
        object ToHome : Navigate()
        data class ToSignUp(val email: String) : Navigate()
    }

    sealed class UpdateUi : SideEffect() {
        data class ShowEmailError(val error: String) : UpdateUi()
        data class ShowPasswordError(val error: String) : UpdateUi()
        data class ShowCredentialsError(val email: String, val password: String) : UpdateUi()
        object HideErrors : UpdateUi()
    }

    sealed class Dialog : SideEffect() {
        data class ShowCredentialsIncorrect(val error: String) : Dialog()
        object ShowUnknownError : Dialog()
        object ShowProgress : Dialog()
        object HideProgress : Dialog()
        object Clear : Dialog()
    }
}