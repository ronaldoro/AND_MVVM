package com.example.test_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_mvvm.Adapter.ContactAdapter
import com.example.test_mvvm.Model.Contact
import com.example.test_mvvm.ViewModel.ContactViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set contactItemClick & contactItemLongClick lambda
        val adapter = ContactAdapter({ contact ->
            // put extras of contact info & start AddActivity
        }, { contact ->
            deleteDialog(contact)
        })

        val lm = LinearLayoutManager(this)
        var main_recycleview = findViewById<RecyclerView>(R.id.main_recycleview)
        main_recycleview.adapter = adapter
        main_recycleview.layoutManager = lm
        main_recycleview.setHasFixedSize(true)


        //Must be created with ViewModelProviders - If an already created ViewModel instance exists, it is returned.
        //contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java) -- deprecated
        contactViewModel = ViewModelProvider(this, ContactViewModel.Factory(application))
            .get(ContactViewModel::class.java)

        //observe lifecycle of Activity or Fragment
        contactViewModel.getAll().observe(this, Observer<List<Contact>> { contacts ->
            adapter.setContacts(contacts!!) // Update UI
        })

        //val contact = Contact(1, "name2", "number2", 'H')
        //contactViewModel.insert(contact)

        //globalScope
        GlobalScope.launch {
            delay(2000L)

            val contact = Contact(1, "name", "number", 'C')
            insertData(contact)
        }
    }

    private suspend fun insertData(contact: Contact) {
        contactViewModel.insert(contact)
    }

    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contact?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->

                GlobalScope.launch {
                    contactViewModel.delete(contact)
                }
            }
        builder.show()
    }
}