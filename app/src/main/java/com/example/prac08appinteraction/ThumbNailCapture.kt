package com.example.prac08appinteraction

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prac08appinteraction.ui.theme.Prac08AppInteractionTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap

class ThumbNailCapture : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prac08AppInteractionTheme {
                ThumbnailCaptureScreen()
            }
        }
    }
}

@Composable
fun ThumbnailCaptureScreen() {
    var thumbnailImage by remember { mutableStateOf<Bitmap?>(null) }

    // This launcher is equivalent to ActivityResultLauncher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            thumbnailImage = bitmap
        } else {
            Log.e("CameraCapture", "Failed to capture image")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp)
    ) {
        Button(
            onClick = {
                cameraLauncher.launch()
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = "Capture Image")
        }

        // Display the thumbnail if available
        thumbnailImage?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }
    }
}
