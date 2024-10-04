package com.example.prac08appinteraction

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

// Define the Student entity for the Room database.
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "contact_name") val name: String,
    @ColumnInfo(name = "contact_phone") val phone: String,
    @ColumnInfo(name = "contact_postal") val postal: String?,
    @ColumnInfo(name = "contact_image") val image: String? // Store the path of the image
)

// Data Access Object, DAO is an interface that defines methods
// for accessing the database.
@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<Contact>

    @Insert
    fun insertContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("DELETE FROM contacts WHERE id = :contactId")
    suspend fun deleteContactById(contactId: Int)
}

// Database class must be an abstract class that extends RoomDatabase
// For each DAO class that is associated with the database, the database class
// must define an abstract method that has zero arguments and returns an
// instance of the DAO class
@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "contact-database"
            ).allowMainThreadQueries().build()
            INSTANCE = instance
            instance
        }
    }
}
