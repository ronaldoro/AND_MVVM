package com.example.test_mvvm.ViewModel

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test_mvvm.Model.Contact
import com.example.test_mvvm.Repository.ContactRepository

//Don't Use Application(Context) because Memory Leak --> just for Room database
class ContactViewModel(application: Application) : AndroidViewModel(application) {

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ContactViewModel(application) as T
        }
    }

    private val repository = ContactRepository(application)
    private val contacts = repository.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return this.contacts
    }

    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    fun delete(contact: Contact) {
        repository.delete(contact)
    }
}