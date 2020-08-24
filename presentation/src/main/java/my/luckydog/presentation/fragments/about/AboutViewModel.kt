package my.luckydog.presentation.fragments.about

import androidx.lifecycle.*
import my.luckydog.interactors.about.ItemChip
import my.luckydog.presentation.R

class AboutViewModel : ViewModel(), LifecycleObserver {

    val form: AboutForm = AboutForm()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun start() {
        form.chips.set(generate())
    }

    fun chipChanges(index: Int, isChecked: Boolean) {
        println("$index $isChecked")
    }

    private fun generate() = listOf(
        ItemChip("first", R.drawable.ic_home_black_24dp, true),
        ItemChip("second", checkable = false),
        ItemChip("third", R.drawable.ic_info_black_24dp, true),
        ItemChip("happiness"),
        ItemChip("family"),
        ItemChip("money", R.drawable.ic_email_black_24dp, true),
        ItemChip("friendship"),
        ItemChip("ice-cream"),
        ItemChip("house", R.drawable.ic_pets_black_24dp, true),
        ItemChip("chip1", checkable = false),
        ItemChip("chip2"),
        ItemChip("chip3", checkable = false),
        ItemChip("chip4"),
        ItemChip("chip5", checkable = false),
        ItemChip("chip6"),
        ItemChip("chip7", checkable = false),
        ItemChip("chip8", R.drawable.ic_pets_black_24dp, true),
        ItemChip("chip9"),
        ItemChip("chip10", R.drawable.ic_pets_black_24dp),
        ItemChip("chip1"),
        ItemChip("chip2"),
        ItemChip("chip3", R.drawable.ic_pets_black_24dp, true),
        ItemChip("chip4"),
        ItemChip("chip5", checkable = false),
        ItemChip("chip6", checkable = false),
        ItemChip("chip7"),
        ItemChip("chip8"),
        ItemChip("chip9"),
        ItemChip("chip10")
    )
}