package com.example.test_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.test_mvvm.Model.Contact
import com.example.test_mvvm.ViewModel.ContactViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Must be created with ViewModelProviders - If an already created ViewModel instance exists, it is returned.
        //contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java) -- deprecated
        contactViewModel = ViewModelProvider(this, ContactViewModel.Factory(application))
            .get(ContactViewModel::class.java)

        //observe lifecycle of Activity or Fragment
        contactViewModel.getAll().observe(this, Observer<List<Contact>> { contacts ->
            // Update UI
        })

    }
}