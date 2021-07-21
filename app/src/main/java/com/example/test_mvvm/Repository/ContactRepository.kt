package com.example.test_mvvm.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.test_mvvm.Model.Contact
import com.example.test_mvvm.Model.ContactDao
import com.example.test_mvvm.Model.ContactDatabase
import kotlinx.coroutines.*
import java.lang.Runnable

class ContactRepository(application: Application,
                        private val externalScope: CoroutineScope = GlobalScope,
                        private val IODispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return contacts
    }

    suspend fun insert(contact: Contact) {
        externalScope.launch(IODispatcher) {
            contactDao.insert(contact)
        }.join()

        //To do -- Change Coroutine
        //try {
        //    val thread = Thread(Runnable {
        //        contactDao.insert(contact) })
        //    thread.start()
        //} catch (e: Exception) { }
    }

    suspend fun delete(contact: Contact) {
        externalScope.launch(IODispatcher) {
            contactDao.delete(contact)
        }.join()

        //To do -- Change Coroutine
        //try {
        //    val thread = Thread(Runnable {
        //        contactDao.delete(contact)
        //    })
        //    thread.start()
        //} catch (e: Exception) { }
    }

}