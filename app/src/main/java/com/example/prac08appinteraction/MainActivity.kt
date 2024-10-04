package com.example.prac08appinteraction

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prac08appinteraction.ui.theme.Prac08AppInteractionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prac08AppInteractionTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current // Get the context here

    Column(modifier = Modifier.padding(64.dp)) {
        Button(onClick = { callButtonClicked(context) }) {
            Text("Call")
        }

        Button(onClick = { viewWebButtonClicked(context) }, modifier = Modifier.padding(top = 16.dp)) {
            Text("View Web")
        }

        Button(onClick = { sendEmailButtonClicked(context) }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Send Email")
        }

        Button(onClick = { openPickContactActivity(context) }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Pick Contact Activity")
        }

        Button(onClick = { openThumbnailActivity(context) }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Thumbnail Activity")
        }

        Button(onClick = { openContactCaptureActivity(context) }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Contact Capture Activity")
        }
    }
}


private fun callButtonClicked(context: Context) {
    val phone = 1234567
    val uri = Uri.parse("tel:$phone")
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = uri
    }
    context.startActivity(intent)
}

private fun viewWebButtonClicked(context: Context) {
    val url = "http://curtin.edu.au"
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = uri
    }

    val pm: PackageManager = context.packageManager
    val resolveInfo: ResolveInfo? = pm.resolveActivity(intent, 0)
    if (resolveInfo != null) {
        Log.d("Name", resolveInfo.activityInfo.packageName)
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "No suitable package found", Toast.LENGTH_LONG).show()
    }
}

private fun sendEmailButtonClicked(context: Context) {
    val mailto = arrayOf("abc@gmail.com")
    val subject = "Test"
    val mailBody = "We are testing our email"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, mailto)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, mailBody)
    }
    context.startActivity(intent)
}


private fun openPickContactActivity(context: Context) {
    val intent = Intent(context, PickContactActivity::class.java)
    context.startActivity(intent)
}

private fun openThumbnailActivity(context: Context) {
    val intent = Intent(context, ThumbNailCapture::class.java)
    context.startActivity(intent)
}

private fun openContactCaptureActivity(context: Context) {
    val intent = Intent(context, ContactCaptureActivity::class.java)
    context.startActivity(intent)
}
