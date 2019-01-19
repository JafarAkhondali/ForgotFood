package ir.a0se.forgotfood.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.livinglifetechway.k4kotlin.onClick
import ir.a0se.forgotfood.R
import android.content.Intent
import android.net.Uri


class AboutFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.about_fragment, container, false)


        v.findViewById<TextView>(R.id.tv_contact_me).onClick {
            val phone = "+989013906827"
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }
        return v

    }


    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }

}