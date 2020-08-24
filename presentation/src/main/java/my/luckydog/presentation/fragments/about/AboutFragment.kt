package my.luckydog.presentation.fragments.about

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import my.luckydog.common.extensions.checkedChanges
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.getViewModel
import my.luckydog.presentation.databinding.FragmentAboutBinding
import my.luckydog.presentation.databinding.FragmentAboutBindingImpl

import kotlin.LazyThreadSafetyMode.NONE

class AboutFragment : Fragment() {

    private val viewModel: AboutViewModel by lazy(NONE) { getViewModel { AboutViewModel() } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAboutBinding.inflate(inflater).also {
        it.form = viewModel.form
        it.lifecycleOwner = viewLifecycleOwner


    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(viewModel)
    }

    override fun onResume() {
        super.onResume()

        (0..chips.childCount).forEach {index ->
            val child = chips.getChildAt(index)
            if (child is CompoundButton)
                child.checkedChanges()
                    .onEach { viewModel.chipChanges(child.tag as Int, it) }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }
}