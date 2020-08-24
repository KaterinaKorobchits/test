package my.luckydog.presentation.fragments.vocabulary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*
import my.luckydog.presentation.R
import java.util.*

class SampleAdapter(context: Context) : RecyclerView.Adapter<SampleAdapter.SampleHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val items = mutableListOf<SampleData>()
    private val random = Random()

    init {
        prepareSampleData()
    }

    private fun prepareSampleData() {
        for (itemNumber in 1..30) {
            items.add(SampleData("Item [$itemNumber]", "description [$itemNumber]"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleHolder {
        return SampleHolder(inflater.inflate(R.layout.item_recycler, parent, false).also {
            //it.setOnClickListener{println("!!!!!")}
        })
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SampleHolder, position: Int) {
        holder.setSampleData(items[position])
    }

    inner class SampleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setSampleData(sampleData: SampleData) {
            itemView.swipeContainer.setSwipeListener(object : SwipeLayout.SwipeListener {
                override fun onSwipe(state: SwipeState) {
                    sampleData.swipeState = state
                }
            })
            itemView.setOnClickListener { println("itemView click") }
            itemView.foregroundContainer.setOnClickListener { println("foregroundContainer click") }
            itemView.titleText.setOnClickListener { println("titleText click") }
            itemView.button1.setOnClickListener { println("left click") }
            itemView.button2.setOnClickListener { println("left click") }
            itemView.button3.setOnClickListener { println("right click") }
            itemView.button4.setOnClickListener { println("right click") }
            itemView.titleText.text = sampleData.title
            itemView.descriptionText.text = sampleData.description
            itemView.swipeContainer.applySwipe(sampleData.swipeState)
        }
    }
}