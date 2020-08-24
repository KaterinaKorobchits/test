package my.luckydog.presentation.dialogs.message

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

class MessageDialogForm {
    val iconRes = ObservableInt()
    val iconDrawable = ObservableField<Drawable>()
    val title = ObservableField<String>()
    val message = ObservableField<String>()
    val positive = ObservableField<String>()
    val negative = ObservableField<String>()
    val neutral = ObservableField<String>()
}