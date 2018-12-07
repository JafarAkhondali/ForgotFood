package ir.a0se.forgotfood.Fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.a0se.forgotfood.Adapters.HungryAdapter
import ir.a0se.forgotfood.R
import ir.a0se.forgotfood.Models.Hungry
import org.jetbrains.anko.toast
import java.util.ArrayList
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import android.app.Activity.RESULT_OK
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest
import ir.a0se.forgotfood.Activities.HungryActivity


const val PICK_CONTACT_REQUEST = 1  // The request code


class HomeFragment : Fragment(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var hungriesArray: ArrayList<Hungry> = ArrayList()



    private fun pickContact() {
        Intent(Intent.ACTION_PICK, Uri.parse("content://contacts")).also { pickContactIntent ->
            pickContactIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE // Show user only contacts w/ phone numbers
            startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST)
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{

        val v = inflater.inflate(R.layout.home_fragment, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = HungryAdapter(
            hungriesArray
        ) { pos:Int ->
            val intent = Intent(context, HungryActivity::class.java)
            intent.putExtra("hungry_id", hungriesArray[pos].id)
                startActivity(intent)
        }

        recyclerView = v.findViewById<RecyclerView>(R.id.rv_hungry_list).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        v.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {

            Dexter.withActivity(activity)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        pickContact()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        context!!.toast("خو من بدون دسترسی چیکار کنم؟ :|")
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest();
                    }
                }).check()
        }

        refreshData()
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            val contactUri = data!!.data
            val projection = {ContactsContract.CommonDataKinds.Phone.NUMBER}
            val cursor = context!!.contentResolver.query(contactUri, null,
                null, null, null);

            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val fullNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val CONTACT_ID= cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)

                val number = cursor.getString(numberIndex)
                val fullname = cursor.getString(fullNameIndex)
                val contactId= cursor.getString(CONTACT_ID)

                val h = Hungry(
                    phoneNumber = number,
                    fullname = fullname,
                    contactId = contactId
                )

                h.save(context!!)



                refreshData()

            }

            cursor.close()
        }



    }


    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        hungriesArray.clear()
        hungriesArray.addAll(Hungry.getAll(context!!))
        viewAdapter.notifyDataSetChanged()

    }


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

    }
}