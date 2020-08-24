package my.luckydog.presentation.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.*
import my.luckydog.presentation.R
import my.luckydog.presentation.core.theme.ThemeRepository
import my.luckydog.presentation.databinding.FragmentSettingsBinding
import javax.inject.Inject

class SettingsFragment : Fragment(), SettingsHandler {

    @Inject
    lateinit var theme: ThemeRepository

    private val form = SettingsForm()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSettingsBinding.inflate(inflater)
        .also {
            it.handler = this
            it.form = form
            it.lifecycleOwner = viewLifecycleOwner
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        form.isDarkTheme.set(theme.current() == R.style.AppTheme_DarkTheme)

        switch_night_theme.setOnCheckedChangeListener { _, isChecked ->
            val themeId = if (isChecked) R.style.AppTheme_DarkTheme else R.style.AppTheme_LightTheme
            activity?.let { theme.apply(it, themeId, true) }
        }
    }

    override fun darkThemeCheckedChanged(checked: Boolean) {
        val themeId = if (checked) R.style.AppTheme_DarkTheme else R.style.AppTheme_LightTheme
        activity?.let { theme.apply(it, themeId, true) }
    }
}