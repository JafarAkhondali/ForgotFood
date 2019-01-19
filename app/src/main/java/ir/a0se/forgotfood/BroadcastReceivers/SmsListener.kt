package ir.a0se.forgotfood.BroadcastReceivers


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import ir.a0se.forgotfood.Models.Hungry
import org.jetbrains.anko.toast
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class SmsListener : BroadcastReceiver() {

    fun sendSMS(con: Context, phoneNumber: String, message: String) {
        try {
            val sms = SmsManager.getDefault()
            sms.sendTextMessage(phoneNumber, CART_HOLDER_PERSON_NUMBER, message, null, null)
        } catch (ex: Exception) {
            Toast.makeText(
                con, "ex: " + ex.message,
                Toast.LENGTH_SHORT
            ).show()

            Log.d("SEND_SMS_GLOBAL", ex.message)
        }

    }


    override fun onReceive(context: Context, intent: Intent) {
        var mobile: String
        var body: String
        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle.get("pdus") as Array<Any>
                for (i in pdusObj.indices) {
                    
                    val currentMessage = SmsMessage.createFromPdu(pdusObj[i] as ByteArray)


                    val phoneNumber = currentMessage.displayOriginatingAddress
                    
                    val message = currentMessage.displayMessageBody
                    mobile = phoneNumber.replace("\\s".toRegex(), "")

                    Hungry.getAll(context).forEach {
                        if (it.phoneNumber.replace(" ","").endsWith(mobile.replace("+98",""))) {
                            body = message.replace("\\s".toRegex(), "+")
                            if(body.matches("\\d+".toRegex())){
                                sendSMS(context, body, "#1#")
                                context.toast("گرسنه ای سیر گشت :)")

                                val persianTime = PersianDate()
                                val dateFormatter = PersianDateFormat("l، j F")
                                val timeFormatter = PersianDateFormat("H:i:s")

                                val timeString = dateFormatter.format(persianTime) + " ساعت: " + timeFormatter.format(persianTime)


                                val from_number= it.phoneNumber
                                val to_number = body


                                val log = ir.a0se.forgotfood.Models.Log(
                                    timestamp = timeString,
                                    from_number = from_number,
                                    to_number = to_number,
                                    fullname = it.fullname,
                                    contactId = it.contactId
                                )


                                log.save(context)

                            }
                        }
                    }

                } // end for loop
            } // bundle is null

        } catch (e: Exception) {
            Log.e("SmsReceiver", "Exception smsReceiver$e")
        }

    }

    companion object {
        private val CART_HOLDER_PERSON_NUMBER = null;
        private val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
        private val TAG = "SMSBroadcastReceiver"
    }
}