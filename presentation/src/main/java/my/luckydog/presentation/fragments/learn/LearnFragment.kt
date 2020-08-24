package my.luckydog.presentation.fragments.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import my.luckydog.common.extensions.move
import my.luckydog.presentation.R
import my.luckydog.presentation.core.DragWrapper
import my.luckydog.presentation.core.MoveItemTouchHelper
import my.luckydog.presentation.core.extensions.setSafeOnClickListener
import my.luckydog.presentation.databinding.FragmentLearnBinding
import javax.inject.Inject

class LearnFragment : Fragment() {

    @Inject
    lateinit var navigation: LearnNavigation

    private val answerAdapter: AnswerAdapter by lazy { AnswerAdapter() }
    private val lettersAdapter: LettersAdapter by lazy { LettersAdapter() }

    private val form = LearnForm()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLearnBinding.inflate(inflater)
        .also {
            val flags = UP or LEFT or RIGHT or DOWN
            val moveCallback: Callback = MoveItemTouchHelper(flags) { from, to ->
                form.answerLetters.move(from, to)
            }
            ItemTouchHelper(moveCallback).attachToRecyclerView(it.rcvAnswer)

            it.answerAdapter = answerAdapter
            it.lettersAdapter = lettersAdapter.apply {
                setClick {
                    form.apply {
                        answerLetters.add(it)
                        letters[letters.indexOf(it)] = ItemLetterChosen(it.id, it.letter)
                    }
                }
            }
            it.form = form
            it.lifecycleOwner = viewLifecycleOwner

            it.rcvLetters.setOnDragListener(DragWrapper<ItemLetter>(it.rcvAnswer) { item ->
                val letter = form.letters.find { it.id == item.id }
                form.apply {
                    answerLetters.remove(item)
                    letters[letters.indexOf(letter)] = ItemLetter(item.id, item.letter)
                }
            })
            it.rcvAnswer.setOnDragListener(DragWrapper<ItemLetter>(it.rcvLetters) { item ->
                form.apply {
                    answerLetters.add(item)
                    letters[letters.indexOf(item)] = ItemLetterChosen(item.id, item.letter)
                }
            })
        }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        form.letters.apply { clear(); addAll(getLetters()) }

        activity?.findViewById<View>(R.id.fab)?.setSafeOnClickListener { navigation.showTest() }
    }

    private fun getLetters(): List<ItemLetter> {
        return listOf(
            ItemLetter(1, "S"),
            ItemLetter(2, "E"),
            ItemLetter(3, "F"),
            ItemLetter(4, "C"),
            ItemLetter(5, "L"),
            ItemLetter(6, "S"),
            ItemLetter(7, "S"),
            ItemLetter(8, "U"),
            ItemLetter(9, "C"),
            ItemLetter(10, "U")
        )
    }
}