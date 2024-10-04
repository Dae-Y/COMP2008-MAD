package com.example.prac08appinteraction

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.prac08appinteraction.ui.theme.Prac08AppInteractionTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager

class PickContactActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prac08AppInteractionTheme {
                ContactPickerScreen()
            }
        }
    }
}

@Composable
fun ContactPickerScreen() {
    var contactName by remember { mutableStateOf<String?>(null) }
    var contactEmail by remember { mutableStateOf<String?>(null) }
    var contactPhone by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Contact picker using ActivityResultContracts.PickContact
    val contactPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact()
    ) { contactUri: Uri? ->
        contactUri?.let {
            contactName = getContactName(context, it)
            contactEmail = getContactEmail(context, it)
            contactPhone = getContactPhone(context, it)
        }
    }

    // Permission launcher for requesting READ_CONTACTS permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, you can launch contact picker
            contactPickerLauncher.launch(null)
        } else {
            // Permission is denied, notify user
            contactName = "Permission Denied"
            contactEmail = null
            contactPhone = null
        }
    }

    Column(modifier = Modifier.padding(64.dp)) {
        Button(onClick = {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is granted, launch contact picker
                contactPickerLauncher.launch(null)
            } else {
                // Request permission if not already granted
                permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }, modifier = Modifier.padding(bottom = 64.dp)) {
            Text("Pick a Contact")
        }
        contactName?.let {
            Text("Name: $it", modifier = Modifier.padding(bottom = 16.dp))
        }
        contactEmail?.let {
            Text("Email: $it", modifier = Modifier.padding(bottom = 16.dp))
        }
        contactPhone?.let {
            Text("Phone: $it", modifier = Modifier.padding(bottom = 16.dp))
        }
    }
}

// Function to get the contact name
private fun getContactName(context: Context, contactUri: Uri): String? {
    val cursor = context.contentResolver.query(
        contactUri,
        arrayOf(ContactsContract.Contacts.DISPLAY_NAME),
        null, null, null
    )
    cursor?.use {
        if (cursor.moveToFirst()) {
            val index = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            index?.let{
                return cursor.getString(index)
            }
        }
    }
    return "No name found"
}

// Function to get the contact email
private fun getContactEmail(context: Context, contactUri: Uri): String? {
    val contactId = getContactId(context, contactUri)
    contactId?.let {
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Email.ADDRESS),
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )
        cursor?.use {
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
                index?.let{
                    return cursor.getString(index)
                }
            }
        }
    }
    return "No email found"
}

private fun getContactPhone(context: Context, contactUri: Uri): String? {
    val contactId = getContactId(context, contactUri)
    contactId?.let {
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )
        cursor?.use {
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                index?.let{
                    return cursor.getString(index)
                }
            }
        }

    }
    return "No phone number found"
}

// Helper function to get the contact ID from URI
private fun getContactId(context: Context, contactUri: Uri): String? {
    val cursor = context.contentResolver.query(
        contactUri,
        arrayOf(ContactsContract.Contacts._ID),
        null, null, null
    )
    cursor?.use {
        if (cursor.moveToFirst()) {
            val index = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            index?.let{
                return cursor.getString(index)
            }
        }
    }
    return null
}


