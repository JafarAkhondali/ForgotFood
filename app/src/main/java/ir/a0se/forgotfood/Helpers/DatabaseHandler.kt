package ir.a0se.forgotfood.Helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseHandler(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
    companion object {
        private var instance: DatabaseHandler? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHandler {
            if (instance == null) {
                instance = DatabaseHandler(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable("Hungries", true,
            "id" to SqlType.create("INTEGER PRIMARY KEY AUTOINCREMENT"),
            "contact_id" to INTEGER,
            "phoneNumber" to TEXT,
            "fullname" to TEXT)

        db.createTable("Logs", true,
            "id" to SqlType.create("INTEGER PRIMARY KEY AUTOINCREMENT"),
            "contact_id" to INTEGER,
            "to_number" to TEXT,
            "from_number" to TEXT,
            "fullname" to TEXT,
            "timestamp" to TEXT)




    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        // db.dropTable("Hungries", true)
    }
}

val Context.database: DatabaseHandler
    get() = DatabaseHandler.getInstance(applicationContext)