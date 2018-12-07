package ir.a0se.forgotfood.Activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView
import ir.a0se.forgotfood.R
import ir.a0se.forgotfood.Fragments.AboutFragment
import ir.a0se.forgotfood.Fragments.HomeFragment


class MainActivity : AppCompatActivity() {


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_main_activity, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val aboutFragment = AboutFragment.newInstance()
                openFragment(aboutFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val morphnav = findViewById<MorphBottomNavigationView>(R.id.navigation)
        morphnav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        morphnav.selectedItemId = R.id.navigation_home

        val homeFragment = HomeFragment.newInstance()
        openFragment(homeFragment)
    }






}
