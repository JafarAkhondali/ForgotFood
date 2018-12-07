package ir.a0se.forgotfood.Adapters

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import ir.a0se.forgotfood.R
import ir.a0se.forgotfood.Models.Hungry
import kotlinx.android.synthetic.main.hungry_item.view.*

class HungryAdapter(private val hungryDataset: ArrayList<Hungry>, val clickListener: (Int) -> Unit) :
    Adapter<HungryAdapter.HungryViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class HungryViewHolder(val hungryLayout: ConstraintLayout) : RecyclerView.ViewHolder(hungryLayout)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): HungryAdapter.HungryViewHolder {
        // create a new view
        val hungryLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.hungry_item, parent, false) as ConstraintLayout
        // set the view's size, margins, paddings and layout parameters

        hungryLayout.isClickable = true
        return HungryViewHolder(hungryLayout)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: HungryViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //   holder.textView.text = hungryDataset.get(position)
        val hungryPerson : Hungry = hungryDataset[position]
        holder.hungryLayout.setOnClickListener{
            clickListener(position)
//            { partItem : PartData -> partItemClicked(partItem) }
        }
        holder.hungryLayout.tv_fullname.text = hungryPerson.fullname
        holder.hungryLayout.tv_phonenumber.text = hungryPerson.phoneNumber
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = hungryDataset.size
}