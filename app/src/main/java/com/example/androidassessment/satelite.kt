package com.example.androidassessment


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun satellite(navController: NavController) {
    val DM_Sans = FontFamily(
        Font(R.font.dm_sans , FontWeight.Normal)
    )

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
            fontFamily = DM_Sans,
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

                Text(
                    text = "Recent Imagery Shows\n mostly healthy crops,\n with some stressed areas\n along northern edge. ",
                    fontSize = 24.sp,
                    color = Color.Black,
                    lineHeight = 24.sp,
                    fontFamily = DM_Sans,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Add space to push FAB to center
        Spacer(modifier = Modifier.weight(1f))

        // 4. Floating Action Button in the center
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(nav.insights)
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
                    painter = painterResource(R.drawable.baseline_mic_24),
                    contentDescription = "Add",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
