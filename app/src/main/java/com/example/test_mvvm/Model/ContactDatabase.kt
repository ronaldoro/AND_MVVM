package com.example.test_mvvm.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun contactDao(): ContactDao

    //for use Database as singleton
    companion object {
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase? {
            if (INSTANCE == null) {

                //Prevent to access other threads
                synchronized(ContactDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ContactDatabase::class.java, "contact")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }

}