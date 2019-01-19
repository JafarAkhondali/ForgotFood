package ir.a0se.forgotfood.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.a0se.forgotfood.Adapters.LogsAdapter
import ir.a0se.forgotfood.Models.Log
import ir.a0se.forgotfood.R


class LogsFragment : Fragment(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var logsArray: ArrayList<Log> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val v = inflater.inflate(R.layout.logs_fragment, container, false)


        viewAdapter = LogsAdapter(logsArray)
        viewManager = LinearLayoutManager(activity)

        v.findViewById<RecyclerView>(R.id.rv_logs_list).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        refreshData()

        return v
    }



    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        logsArray.clear()
        logsArray.addAll(Log.getAll(context!!))
        viewAdapter.notifyDataSetChanged()

    }


    companion object {
        fun newInstance(): LogsFragment = LogsFragment()
    }

}