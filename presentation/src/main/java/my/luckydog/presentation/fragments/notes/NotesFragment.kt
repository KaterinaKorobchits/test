package my.luckydog.presentation.fragments.notes

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dagger.Lazy
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import my.luckydog.common.Cryptor
import my.luckydog.common.CryptorImpl
import my.luckydog.common.DeCryptor
import my.luckydog.common.EnCryptor
import my.luckydog.interactors.notes.NotesInteractor
import my.luckydog.interactors.notes.SideEffect.Dialog
import my.luckydog.interactors.notes.SideEffect.Dialog.*
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.getViewModel
import my.luckydog.presentation.core.extensions.setSafeOnClickListener
import my.luckydog.presentation.core.permissions.CoPermissions
import my.luckydog.presentation.databinding.FragmentNotesBinding
import my.luckydog.presentation.fragments.notes.dialogs.NotesDialogs
import javax.inject.Inject
import javax.inject.Provider
import kotlin.LazyThreadSafetyMode.NONE

class NotesFragment : Fragment() {

    @Inject
    lateinit var interactor: Lazy<NotesInteractor>

    @Inject
    lateinit var dialogs: Lazy<NotesDialogs>

    @Inject
    lateinit var adapter: Provider<NoteAdapter>

    private val viewModel: NotesViewModel by lazy(NONE) {
        getViewModel { NotesViewModel(interactor.get(), NotesForm(), dialogs.get()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNotesBinding.inflate(inflater)
        .also {
            it.adapter = adapter.get().apply {

                //viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                clickFlow()
                    .onEach { item -> println("CLICk: $item") }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
                // }

                //  setClick { note -> viewModel.clickNote(note.word) }
            }
            it.form = viewModel.form
            it.lifecycleOwner = viewLifecycleOwner
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dialogs.prepare(requireContext())
        viewModel.getAll()

        //swipeContainer.setOnRefreshListener { viewModel.getAll() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.showTranslate.observe(viewLifecycleOwner, Observer { event ->
            event.getContent()
                ?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { event ->
            // event.getContent()?.let { swipeContainer.isRefreshing = !it }
        })

        viewModel.showDialog.observe(viewLifecycleOwner, Observer { event ->
            event.getContent()?.let { showDialog(it) }
        })

        activity?.findViewById<View>(R.id.fab)?.setSafeOnClickListener {
            viewModel.dialogs.enterWord(requireContext(), viewModel.addNote)
        }
    }

    override fun onResume() {
        super.onResume()

        requestPermissions(arrayOf(Manifest.permission.CAMERA), 2)

        CoPermissions.getInstance(childFragmentManager)
            .request(

                Manifest.permission.CAMERA)
            .onEach {
                println(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.dialogs.dismiss()
    }

    private fun showDialog(dialog: Dialog) = when (dialog) {
        is ShowDetectLangFail -> viewModel.dialogs.showDetectLangError(requireContext())
        is ShowUnknownError -> viewModel.dialogs.showUnknownError(requireContext())
        is ShowProgress -> viewModel.dialogs.showProgress(requireContext())
        is HideProgress -> viewModel.dialogs.hideProgress()
    }
}


