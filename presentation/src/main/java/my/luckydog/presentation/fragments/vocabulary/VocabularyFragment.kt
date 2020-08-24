package my.luckydog.presentation.fragments.vocabulary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_vocabulary.*
import my.luckydog.presentation.R

class VocabularyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_vocabulary, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*val adapter = Adapter(listOf("1", "2", "3", "4", "5", "6", "1", "2", "3", "4", "5", "6"))
        val swipeHandler = Adapter.SwipeHandler(adapter)
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        adapter.setSwipeHandler(swipeHandler)
        recycler_view.adapter = adapter
        itemTouchHelper.attachToRecyclerView(recycler_view)*/

        /*val adapter = RecyclerAdapter(requireContext(), createList(20))
        recycler_view.adapter = adapter*/

        val adapter = SampleAdapter(requireContext())
        val layoutManager = LinearLayoutManager(requireContext())
          //  SwipeMenuLinearLayoutManager(requireContext())

        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
    }

    private fun createList(n: Int): List<String>? {
        val list: MutableList<String> = ArrayList()
        for (i in 0 until n) {
            list.add("View $i")
        }
        return list
    }
}