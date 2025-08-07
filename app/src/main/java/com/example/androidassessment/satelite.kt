package com.example.androidassessment


import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun satellite(
    navController: NavController ,
    viewModel: SpeechRecognitionViewModel = viewModel(),
    aiViewModel : AiViewModel = viewModel()
)
{
    var hasPermission by remember { mutableStateOf(false) }

    var response = aiViewModel.uiState.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
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
            text = "Satellite Imagery",
            fontSize = 24.sp,
            fontFamily = dmsans,
            fontWeight = FontWeight.Bold,
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

                Image(
                    painter = painterResource(id = R.drawable.farms),
                    contentDescription = "Card Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

               if (response.toString().isNotEmpty()){

                   Text(
                       text =  response.value.messages.toString() ,
                       fontSize = 24.sp,
                       color = Color.Black,
                       lineHeight = 24.sp,
                       fontFamily = dmsans,
                       textAlign = TextAlign.Justify,
                       modifier = Modifier.fillMaxWidth()
                   )
               }
                else{
                   Text(
                       text = "Start Observing Whats Going on.",
                       fontSize = 24.sp,
                       color = Color.Black,
                       lineHeight = 24.sp,
                       fontFamily = dmsans,
                       textAlign = TextAlign.Justify,
                       modifier = Modifier.fillMaxWidth()
                   )
               }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            FloatingActionButton(
                onClick = {
                    when {
                        !hasPermission -> {
                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                        viewModel.isRecording -> {
                            viewModel.stopRecording()
                            if (viewModel.recognizedText.isNotEmpty()){
                             aiViewModel.sendMessage(viewModel.recognizedText)
                            }
                        }
                        else -> {
                            viewModel.startRecording()
                        }
                    }
                },
                modifier = Modifier.size(70.dp).clip(CircleShape),
                containerColor = Color(0xFFD4AF37),
                contentColor = Color.Black,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                )
            ) {

                Icon(
                    painter = if (viewModel.isRecording) painterResource(R.drawable.mic_off)  else painterResource(R.drawable.baseline_mic_24),
                    contentDescription = "Record",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
