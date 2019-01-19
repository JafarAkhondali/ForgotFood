package ir.a0se.forgotfood.Models

import android.content.Context
import ir.a0se.forgotfood.Helpers.database
import org.jetbrains.anko.db.*

//import com.github.



private const val TALBE_NAME = "logs"
data class Log(
//

    /*
    id,
    contactId,
    to_number,
    from_number,
    fullname,
    timestamp
    *
    * */
    public var id: String="-1",
    public var contactId: String = "-1",
    public var to_number: String,
    public var from_number: String,
    public var fullname: String,
    public var timestamp: String
) {

    public fun save(context: Context) {
        context.database.use {
            insert(
                TALBE_NAME,
                "contact_id" to contactId,
                "to_number" to to_number,
                "from_number" to from_number,
                "fullname" to fullname,
                "timestamp" to timestamp
            )
        }
    }


    companion object {
        fun getAll(context: Context): List<Log> {
            var logs = ArrayList<Log>()

            context.database.use {

                select(TALBE_NAME,"*")
                    .orderBy("id", SqlOrderDirection.DESC)
                    .parseList(object : MapRowParser<List<Log>> {
                        override fun parseRow(columns: Map<String, Any?>): List<Log> {
                            val id = columns.getValue("id").toString()
                            val contactId= columns.getValue("contact_id").toString()
                            val to_number = columns.getValue("to_number").toString()
                            val from_number = columns.getValue("from_number").toString()
                            val fullname = columns.getValue("fullname").toString()
                            val timestamp= columns.getValue("timestamp").toString()

                            val log = Log(
                                id,
                                contactId,
                                to_number,
                                from_number,
                                fullname,
                                timestamp
                            )

                            logs.add(log)
                            return logs
                        }
                    })

            }
            return logs
        }

    }
}