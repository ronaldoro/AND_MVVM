package com.example.test_mvvm.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.test_mvvm.Model.Contact
import com.example.test_mvvm.Model.ContactDao
import com.example.test_mvvm.Model.ContactDatabase

class ContactRepository(application: Application) {

    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return contacts
    }

    fun insert(contact: Contact) {
        //To do -- Change Coroutine
        try {
            val thread = Thread(Runnable {
                contactDao.insert(contact) })
            thread.start()
        } catch (e: Exception) { }
    }

    fun delete(contact: Contact) {
        //To do -- Change Coroutine
        try {
            val thread = Thread(Runnable {
                contactDao.delete(contact)
            })
            thread.start()
        } catch (e: Exception) { }
    }

}