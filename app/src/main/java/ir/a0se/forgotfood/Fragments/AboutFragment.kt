package ir.a0se.forgotfood.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.a0se.forgotfood.R

class AboutFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.about_fragment, container, false)

    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }

}