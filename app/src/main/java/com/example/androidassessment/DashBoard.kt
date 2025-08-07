package com.example.androidassessment

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidassessment.ui.theme.dmsans

@Composable
fun DashboardScreen(navController: NavController) {


   Scaffold(
       modifier = Modifier.fillMaxSize(),
   )
    { padding ->
       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(24.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.spacedBy(24.dp)
       ) {

           Text(
               text = "Welcome to",
               fontSize = 28.sp,
               fontFamily = dmsans,
               fontWeight = FontWeight.Bold,
               color = Color.White,
               textAlign = TextAlign.Center,
               modifier = Modifier.padding(top = 40.dp)
           )

           Text(
               text = "HarvestorAI",
               fontSize = 40.sp,
               fontFamily = dmsans,
               modifier = Modifier.fillMaxWidth(),
               fontWeight = FontWeight.Bold,
               color = Color.White,
               textAlign = TextAlign.Center,
           )


           // Image View
           Image(
               painter = painterResource(id = R.drawable.logo), // Replace with your image resource
               contentDescription = "Dashboard Image",
               modifier = Modifier
                   .size(300.dp)
                   .padding(16.dp),
               contentScale = ContentScale.Fit
           )

           Text(
               text = "Your intelligent angel\n watching ever your farm",
               fontSize = 24.sp,
               color = Color.White,
               textAlign = TextAlign.Center,
               fontFamily = dmsans,
               lineHeight = 24.sp,
               modifier = Modifier.padding(horizontal = 16.dp)
           )

           Spacer(modifier = Modifier.weight(1f))

           Button(
               onClick = {
                   navController.navigate(nav.cropScan)
               },
               modifier = Modifier
                   .fillMaxWidth()
                   .height(56.dp)
                   .padding(horizontal = 32.dp),
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(0xFFD4AF37) // Golden color
               ),
               shape = RoundedCornerShape(28.dp),
               elevation = ButtonDefaults.buttonElevation(
                   defaultElevation = 8.dp,
                   pressedElevation = 4.dp
               )
           ) {
               Text(
                   text = "Get Started",
                   fontSize = 18.sp,
                   fontFamily = dmsans,
                   fontWeight = FontWeight.SemiBold,
                   color = Color.Black
               )
           }
           Spacer(modifier = Modifier.height(32.dp))
       }
   }
}
