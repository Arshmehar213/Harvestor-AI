package com.example.androidassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import com.example.androidassessment.ui.theme.AndroidAssessmentTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidassessment.ViewModel.AiViewModel
import com.example.androidassessment.ViewModel.SpeechRecognitionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val aiViewModel = ViewModelProvider(this).get(AiViewModel::class.java)
        val speechViewModel = ViewModelProvider(this).get(SpeechRecognitionViewModel::class.java)
        enableEdgeToEdge()
        setContent {
            AndroidAssessmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   val pad = innerPadding
                   val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = nav.homePage
                    ) {
                        composable(nav.homePage){ Homescreen(navController) }
                        composable(nav.cropScan){ CropScanner(navController) }
                        composable(nav.dashboard){  DashboardScreen(navController)  }
                        composable(nav.insights){ Insights(navController) }
                        composable(nav.satellite){ satellite(navController) }
                        composable(nav.chatModel){ ChatScreen(navController = navController) }
                    }
                }
            }
        }
    }
}
