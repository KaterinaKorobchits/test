package my.luckydog.interactors.signup

sealed class SideEffect {

    sealed class Navigate : SideEffect() {
        data class ToSignIn(val email: String) : Navigate()
        object ToHome : Navigate()
        object ToTerms : Navigate()
    }

    sealed class UpdateUi : SideEffect() {
        data class ShowEmailError(val error: String) : UpdateUi()
        data class ShowPasswordError(val error: String) : UpdateUi()
        data class ShowCredentialsError(val email: String, val password: String) : UpdateUi()
        object HideErrors : UpdateUi()
    }

    sealed class Dialog : SideEffect() {
        object ShowUnknownError : Dialog()
        object ShowProgress : Dialog()
        object HideProgress : Dialog()
        object Clear : Dialog()
    }
}