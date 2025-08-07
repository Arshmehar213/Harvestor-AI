package com.example.androidassessment

import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidassessment.ViewModel.AiViewModel
import com.example.androidassessment.ViewModel.SpeechRecognitionViewModel
import com.example.androidassessment.ui.theme.dmsans

@Composable
fun CropScanner(navController: NavController , viewModel: SpeechRecognitionViewModel = viewModel() , aiViewModel: AiViewModel = viewModel()) {

    var hasPermission by remember { mutableStateOf(false) }
    var takenPicture by remember { mutableStateOf<Bitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { imageBitmap ->
            takenPicture = imageBitmap
        }
    )

    val cameraPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()) { isGranted->
        if (isGranted){
            hasPermission = isGranted
            cameraLauncher.launch()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = {
                   navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

        }

        Text(
            text = "Scan Crop",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = dmsans,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF5F5DC)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

               if (takenPicture != null){
                   Image(
                       bitmap = takenPicture!!.asImageBitmap(),
                          contentDescription = "Card Image",
                          modifier = Modifier
                              .fillMaxWidth()
                              .height(300.dp)
                              .clickable { if (hasPermission){ cameraPermission.launch(Manifest.permission.CAMERA) } },
                          contentScale = ContentScale.Crop
                      )
               }
                else{
                   Image(
                       painter = painterResource(R.drawable.take_picture),
                       contentDescription = "Card Image",
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(300.dp)
                           .clickable { cameraPermission.launch(Manifest.permission.CAMERA) },
                   )
               }

                Text(
                    text = "Diagnosis:\nMagnasium deficiency,\nApply magnasium\nsulfate to correct plants\nto correct the deficiency ",
                    fontSize = 24.sp,
                    color = Color.Black,
                    lineHeight = 24.sp,
                    fontFamily = dmsans,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(9.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FloatingActionButton(
                    onClick = {
                     if (viewModel.isRecording){
                         viewModel.stopRecording()
                         if (viewModel.recognizedText.isNotEmpty() && takenPicture != null){
                             aiViewModel.sendImageInput(takenPicture!!, viewModel.recognizedText)
                         }
                     }
                        else{
                            viewModel.startRecording()
                     }
                    },
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape),
                    containerColor = Color(0xFFD4AF37),
                    contentColor = Color.Black,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 12.dp
                    )
                ) {

                    Icon(
                        painter = painterResource(R.drawable.baseline_mic_24),
                        contentDescription = "Add",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(9.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
