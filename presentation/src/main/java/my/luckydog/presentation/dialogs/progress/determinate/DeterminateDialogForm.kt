package my.luckydog.presentation.dialogs.progress.determinate

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

class DeterminateDialogForm {
    val text = ObservableField<String>()
    val progress = ObservableInt()
    val max = ObservableInt()
    val min = ObservableInt()
}