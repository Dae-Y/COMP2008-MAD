package com.example.prac08appinteraction

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.*
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.prac08appinteraction.ui.theme.Prac08AppInteractionTheme
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ContactCaptureActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Prac08AppInteractionTheme {
                ContactCaptureScreen()
            }
        }
    }
}

@Composable
fun ContactCaptureScreen() {
    var contactName by remember { mutableStateOf<String?>(null) }
    var contactPhone by remember { mutableStateOf<String?>(null) }
    var contactPostal by remember { mutableStateOf<String?>(null) }
    var thumbnailImage by remember { mutableStateOf<Bitmap?>(null) }
    var contacts by remember { mutableStateOf(emptyList<Contact>()) }
    var showContacts by remember { mutableStateOf(false) }
    var deleteContactId by remember { mutableStateOf("") }

    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val contactDao = db.contactDao()

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            thumbnailImage = bitmap
        } else {
            Log.e("CameraCapture", "Failed to capture image")
        }
    }

    val contactPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact()
    ) { contactUri: Uri? ->
        contactUri?.let {
            val contactId = getContactId(context, it)
            contactName = getContactName(context, it)
            contactPhone = getContactPhone(context, it)
            contactPostal = contactId?.let { id -> getContactPostal(context, id) }
            thumbnailImage = contactId?.let { id -> getContactPhoto(context, id) }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            contactPickerLauncher.launch(null)
        } else {
            contactName = "Permission Denied"
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Row to hold buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            // Button to pick contact
            Button(onClick = {
                if (isContactPermissionGranted(context)) {
                    contactPickerLauncher.launch(null)
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                }
            }) {
                Text("Pick Contact")
            }

            // Button to capture image
            Button(onClick = {
                cameraLauncher.launch()
            }) {
                Text("Capture Image")
            }

            // Button to save contact
            Button(onClick = {
                saveContact(context, contactDao, contactName, contactPhone, contactPostal, thumbnailImage)
                contacts = contactDao.getAllContacts() // Refresh the contact list after saving
            }) {
                Text("Save Contact")
            }
        }

        // Display contact information
        contactName?.let { Text("Name: $it") }
        contactPhone?.let { Text("Phone: $it") }
        thumbnailImage?.let {
            Image(bitmap = it.asImageBitmap(), contentDescription = "Thumbnail Image", modifier = Modifier.size(100.dp))
        }

        Spacer(modifier = Modifier.height(14.dp))


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            // Button to show the list of saved contacts
            Button(onClick = {
                showContacts = !showContacts
                if (showContacts) {
                    contacts = contactDao.getAllContacts() // Fetch contacts when showing the list
                }
            }) {
                Text("Show List")
            }

            // Button to delete a contact
            Button(onClick = {
                deleteContact(context, contactDao, deleteContactId.toIntOrNull())
                contacts = contactDao.getAllContacts() // Refresh the contact list after deletion
                deleteContactId = "" // Reset the ID input after deletion
            }) {
                Text("Delete Contact")
            }

            // Text field to enter the contact ID for deletion
            TextField(
                value = deleteContactId,
                onValueChange = { deleteContactId = it },
                label = { Text("Enter Contact ID") }, // Changed label text
                modifier = Modifier.width(150.dp)
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Display the list of saved contacts using Card
        if (showContacts) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(contacts) { contact ->
                    SingleContactCard(contact)
                }
            }
        }
    }
}


// Helper function to delete contact from Room database
private fun deleteContact(context: Context, contactDao: ContactDao, contactId: Int?) {
    if (contactId != null) {
        (context as? ComponentActivity)?.lifecycleScope?.launch {
            contactDao.deleteContactById(contactId) // Call the delete function in DAO
        }
    }
}


// Helper function to check contact permission
private fun isContactPermissionGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
}

// Helper function to save contact to Room database
private fun saveContact(context: Context, contactDao: ContactDao, name: String?, phone: String?, postal: String?, image: Bitmap?) {
    if (!name.isNullOrEmpty() && !phone.isNullOrEmpty()) {
        val imagePath = image?.let { saveImageToInternalStorage(context, it) } ?: getDefaultImagePath(context)
        val contact = Contact(name = name, phone = phone, postal = postal, image = imagePath)
        (context as? ComponentActivity)?.lifecycleScope?.launch {
            contactDao.insertContact(contact)
        }
    }
}

// Helper function to get the default image path
private fun getDefaultImagePath(context: Context): String {
    val defaultImageResId = R.drawable.default_image // Replace with your default image name
    val defaultImage = BitmapFactory.decodeResource(context.resources, defaultImageResId)
    return saveImageToInternalStorage(context, defaultImage)
}

// Helper function to save image to internal storage
private fun saveImageToInternalStorage(context: Context, bitmap: Bitmap): String {
    val filename = UUID.randomUUID().toString() + ".png"
    val file = File(context.filesDir, filename)
    FileOutputStream(file).use { fos ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
    }
    return file.absolutePath
}

// Functions to retrieve contact information
private fun getContactName(context: Context, contactUri: Uri): String? {
    val cursor = context.contentResolver.query(
        contactUri,
        arrayOf(Contacts.DISPLAY_NAME),
        null, null, null
    )
    cursor?.use {
        if (it.moveToFirst()) {
            return it.getString(it.getColumnIndexOrThrow(Contacts.DISPLAY_NAME))
        }
    }
    return "No name found"
}

private fun getContactPhone(context: Context, contactUri: Uri): String? {
    val contactId = getContactId(context, contactUri)
    contactId?.let {
        val cursor = context.contentResolver.query(
            CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(CommonDataKinds.Phone.NUMBER),
            "${CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )
        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndexOrThrow(CommonDataKinds.Phone.NUMBER))
            }
        }
    }
    return "No phone found"
}

private fun getContactId(context: Context, contactUri: Uri): String? {
    val cursor = context.contentResolver.query(
        contactUri,
        arrayOf(Contacts._ID),
        null, null, null
    )
    cursor?.use {
        if (it.moveToFirst()) {
            return it.getString(it.getColumnIndexOrThrow(Contacts._ID))
        }
    }
    return null
}



// Helper function to retrieve the contact's postal address
private fun getContactPostal(context: Context, contactId: String): String {
    val cursor = context.contentResolver.query(
        CommonDataKinds.StructuredPostal.CONTENT_URI,
        arrayOf(CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS),
        "${CommonDataKinds.StructuredPostal.CONTACT_ID} = ?",
        arrayOf(contactId),
        null
    )
    cursor?.use {
        if (it.moveToFirst()) {
            return it.getString(it.getColumnIndexOrThrow(CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS))
        }
    }
    return "none" // Return "none" if no address is found
}

// Helper function to retrieve the contact's photo
private fun getContactPhoto(context: Context, contactId: String): Bitmap? {
    val cursor = context.contentResolver.query(
        ContactsContract.Data.CONTENT_URI,
        arrayOf(ContactsContract.CommonDataKinds.Photo.PHOTO),
        "${ContactsContract.Data.CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?",
        arrayOf(contactId, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE),
        null
    )
    cursor?.use {
        if (it.moveToFirst()) {
            val photoBlob = it.getBlob(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Photo.PHOTO))
            return BitmapFactory.decodeByteArray(photoBlob, 0, photoBlob.size)
        }
    }
    return null // Return null if no photo is found
}

@Composable
fun SingleContactCard(contact: Contact) {
    val context = LocalContext.current
    val bitmap = BitmapFactory.decodeFile(contact.image)

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = {
            Toast.makeText(context, "${contact.name} is clicked", Toast.LENGTH_SHORT).show()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Contact Image",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop // Adjust this as per your requirement
                )
            }
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                Text(text = "Contact ID: ${contact.id}")
                Text(text = "Name: ${contact.name}")
                Text(text = "Phone: ${contact.phone}")
                Text(text = "Address: ${contact.postal}")
            }
        }
    }
}