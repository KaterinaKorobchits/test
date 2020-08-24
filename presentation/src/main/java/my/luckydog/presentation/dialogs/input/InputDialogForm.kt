package my.luckydog.presentation.dialogs.input

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

class InputDialogForm {
    val iconRes = ObservableInt()
    val iconDrawable = ObservableField<Drawable>()
    val title = ObservableField<String>()
    val message = ObservableField<String>()
    val input = ObservableField<String>()
    val positive = ObservableField<String>()
    val negative = ObservableField<String>()
    val neutral = ObservableField<String>()

    internal fun clearInput() {
        input.set("")
    }
}