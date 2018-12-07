package ir.a0se.forgotfood.BroadcastReceivers


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast

class SmsListener : BroadcastReceiver() {

    fun sendSMS(con: Context, phoneNumber: String, message: String) {
        try {
            val sms = SmsManager.getDefault()
            sms.sendTextMessage(phoneNumber, CART_HOLDER_PERSON_NUMBER, message, null, null)
            Toast.makeText(
                con, "Sending",
                Toast.LENGTH_SHORT
            ).show()
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
                    if (mobile.endsWith(HUNGRY_PERSON_NUMBER)) {
                        body = message.replace("\\s".toRegex(), "+")
                        //if(body.matches("\\d+")){
                        sendSMS(context, body, "#1#")
                        //}
                    }
                } // end for loop
            } // bundle is null

        } catch (e: Exception) {
            Log.e("SmsReceiver", "Exception smsReceiver$e")
        }

    }

    companion object {

        private val HUNGRY_PERSON_NUMBER = "9013906827"
        private val CART_HOLDER_PERSON_NUMBER = "+989164911580"


        private val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
        private val TAG = "SMSBroadcastReceiver"
    }
}