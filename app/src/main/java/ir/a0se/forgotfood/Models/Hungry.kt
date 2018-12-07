package ir.a0se.forgotfood.Models

import android.content.Context
import ir.a0se.forgotfood.Helpers.database
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast

//import com.github.
val TALBE_NAME = "Hungries"
data class Hungry(
    public var id: String="-1",
    public var contactId: String = "-1",
    public var phoneNumber: String,
    public var fullname: String
) {

    public fun save(context: Context) {
        context.database.use {

            insert(
                TALBE_NAME,
                "contact_id" to contactId,
                "fullname" to fullname,
                "phoneNumber" to phoneNumber
            )

            context.toast("به لیست مستضعفین اضافه شد‌:|")
        }
    }


    companion object {
        fun getAll(context: Context): List<Hungry> {
            var hugnries = ArrayList<Hungry>()

            context.database.use {

                select(TALBE_NAME, "id", "contact_id", "fullname", "phoneNumber")
                    .parseList(object : MapRowParser<List<Hungry>> {
                        override fun parseRow(columns: Map<String, Any?>): List<Hungry> {
                            val id = columns.getValue("id").toString()
                            val contactid = columns.getValue("contact_id").toString()
                            val fullname = columns.getValue("fullname").toString()
                            val phonenumber = columns.getValue("phoneNumber").toString()

                            val note = Hungry(
                                id,
                                contactid,
                                phonenumber,
                                fullname
                            )

                            hugnries.add(note)
                            return hugnries
                        }
                    })

            }
            return hugnries
        }

        fun find(context: Context,hungry_id: String): Hungry{
            var hungry = Hungry("","","","")
            context.database.use{
                select(TALBE_NAME, "id", "contact_id", "fullname", "phoneNumber")
                    .whereArgs("(id = {hungry_id})",
                        "hungry_id" to hungry_id)
                    .parseSingle(object : MapRowParser<Hungry>{
                        override fun parseRow(columns: Map<String, Any?>): Hungry {
                            val id = columns.getValue("id").toString()
                            val contactid = columns.getValue("contact_id").toString()
                            val fullname = columns.getValue("fullname").toString()
                            val phonenumber = columns.getValue("phoneNumber").toString()

                            hungry = Hungry(id, contactid, phonenumber, fullname)
                            return hungry
                        }
                    })
            }
            return hungry
        }

        fun delete(context: Context,hungry_id: String){
            context.database.use{
                delete(TALBE_NAME, "id = {hungry_id}", "hungry_id" to hungry_id)
            }
        }

    }
}