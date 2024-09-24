package com.example.prac07database

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
@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "student_name") val name: String,
    @ColumnInfo(name = "student_age") val age: Int
)

// Data Access Object, DAO is an interface that defines methods
// for accessing the database.
@Dao
interface StudentDao {
    @Query("SELECT * FROM students") // get all students
    fun getAllStudents(): List<Student>

    @Insert
    fun insertStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)
}

// Database class must be an abstract class that extends RoomDatabase
// For each DAO class that is associated with the database, the database class
// must define an abstract method that has zero arguments and returns an
// instance of the DAO class
@Database(entities = [Student::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
}

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "student-database"
            ).allowMainThreadQueries().build()
            // we are using main thread, we learn multi-threading later.

            INSTANCE = instance
            instance
        }
    }
}
