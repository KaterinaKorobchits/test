package my.luckydog.presentation.dialogs.progress.determinate

import androidx.databinding.ObservableField

class DeterminateDialogForm {
    val text = ObservableField<String>()
    val progress = ObservableField<Int>()
    val max = ObservableField<Int>()
    val min = ObservableField<Int>()
}