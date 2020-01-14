package my.luckydog.presentation.dialogs.message

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField

class MessageDialogForm {
    val iconRes = ObservableField<Int>()
    val iconDrawable = ObservableField<Drawable>()
    val title = ObservableField<String>()
    val message = ObservableField<String>()
}