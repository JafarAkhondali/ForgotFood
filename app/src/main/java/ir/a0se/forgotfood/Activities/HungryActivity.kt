package ir.a0se.forgotfood.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ir.a0se.forgotfood.R
import ir.a0se.forgotfood.Models.Hungry
import org.jetbrains.anko.toast


class HungryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hungry)

        val id = intent.extras["hungry_id"].toString()


        val hungry= Hungry.find(applicationContext,id)

        findViewById<TextView>(R.id.tv_hungry_fullname).text = hungry.fullname
        findViewById<TextView>(R.id.tv_hungry_number).text = hungry.phoneNumber
//        findViewById<Switch>(R.id.sw_hungry_active).isEnabled = true

        findViewById<Button>(R.id.btn_delete).setOnClickListener {
            Hungry.delete(applicationContext,id)
            toast("خیلی نامردی ...")
            finish()
//            alert("جدی ؟!", "مطمئنی میخوای این مظلوم گرسنه رو حذف کنی؟") {
//                yesButton {
//                    Hungry.delete(applicationContext,id)
//                    applicationContext.toast("خیلی نامردی ...")
//                    finish()
//                }
//                noButton {
//                    applicationContext.toast("میدونستم دل پاکتره این حرفایی...")
//                }
//            }.show()
        }

    }

}
