package ir.a0se.forgotfood.Adapters


import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AbsoluteLayout
import ir.a0se.forgotfood.R
import ir.a0se.forgotfood.Models.Log
import kotlinx.android.synthetic.main.log_item.view.*
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat



//import kotlinx.android.synthetic.main.hungry_item.view.*

class LogsAdapter(private val logDataset: ArrayList<Log>) :
    Adapter<LogsAdapter.LogViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class LogViewHolder(val logLayout: AbsoluteLayout) : RecyclerView.ViewHolder(logLayout)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): LogsAdapter.LogViewHolder {
        // create a new view
        val logLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.log_item, parent, false) as AbsoluteLayout
        // set the view's size, margins, paddings and layout parameters

        logLayout.isClickable = true
        return LogViewHolder(logLayout)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //   holder.textView.text = logDataset.get(position)
        val log : Log = logDataset[position]
        holder.logLayout.tv_log_from_number.text = log.from_number
        holder.logLayout.tv_log_to_number.text = log.to_number
        holder.logLayout.tv_log_fullname.text = log.fullname

        val timestamp = log.timestamp

        holder.logLayout.tv_log_time.text = timestamp


//        holder.logLayout.tv_fullname.text = log.fullname
//        holder.logLayout.tv_phonenumber.text = log.phoneNumber
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = logDataset.size
}