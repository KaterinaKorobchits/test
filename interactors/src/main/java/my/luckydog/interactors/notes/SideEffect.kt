package my.luckydog.interactors.notes

sealed class SideEffect {

    sealed class UpdateUi : SideEffect() {
        data class ShowTranslate(val text: String) : UpdateUi()
        data class ShowAllNotes(val notes: List<ItemNote>) : UpdateUi()
        data class AddNote(val note: ItemNote) : UpdateUi()
    }

    sealed class Dialog : SideEffect() {
        object ShowDetectLangFail: Dialog()
        object ShowUnknownError : Dialog()
        object ShowProgress : Dialog()
        object HideProgress : Dialog()
    }
}