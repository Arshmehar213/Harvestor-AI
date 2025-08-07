package com.example.androidassessment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidassessment.ui.theme.dmsans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homescreen(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Harvestor Ai", color = Color.White , fontFamily = dmsans )
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Handle navigation icon click */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Handle more action */ }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E88E5)
                )
            )
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier.padding(innerpadding)
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedCard (
                    modifier = Modifier.clickable { navController.navigate(nav.cropScan) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.scan_crop),
                        contentDescription = "Scan Crop",
                        modifier = Modifier.size(150.dp)
                            .padding(12.dp)
                    )
                    Text(text = "Scan Crop" , modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 12.dp) , fontFamily = dmsans)
                }
                OutlinedCard(
                    modifier = Modifier.clickable { navController.navigate(nav.satellite) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.location_pin),
                        contentDescription = "Location",
                        modifier = Modifier.size(150.dp)
                            .padding(12.dp)
                    )
                    Text(text = "Satelite" , modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 12.dp) , fontFamily = dmsans)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedCard(
                    modifier = Modifier.clickable { navController.navigate(nav.insights) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.insights),
                        contentDescription = "Scan Crop",
                        modifier = Modifier.size(150.dp)
                            .padding(12.dp)
                    )
                    Text(text = "Insights" , modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 12.dp) , fontFamily = dmsans )
                }
                OutlinedCard(
                    modifier = Modifier.clickable { navController.navigate(nav.chatModel) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.chat_bot),
                        contentDescription = "chat",
                        modifier = Modifier.size(150.dp)
                            .padding(12.dp)
                    )
                    Text(text = "Chat Bot" , modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 12.dp) , fontFamily = dmsans)
                }
            }
        }
    }
}