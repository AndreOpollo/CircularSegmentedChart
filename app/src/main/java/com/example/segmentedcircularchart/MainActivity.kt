package com.example.segmentedcircularchart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.segmentedcircularchart.ui.theme.SegmentedCircularChartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SegmentedCircularChartTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val appUsages = listOf<AppUsage>(
                        AppUsage(
                            name = "Whatsapp",
                            durationMinutes = 15,
                            color = Color.Magenta,
                            icon = Icons.Rounded.Face,
                        ),
                        AppUsage(
                            name = "Instagram",
                            durationMinutes = 24,
                            color = Color.Cyan,
                            icon = Icons.Rounded.Home,
                        ),
                        AppUsage(
                            name = "Youtube",
                            durationMinutes = 52,
                            color = Color.Red,
                            icon = Icons.Rounded.Call,
                        ),
                        AppUsage(
                            name = "Contacts",
                            durationMinutes = 30,
                            color = Color.Gray,
                            icon = Icons.Rounded.Settings,
                        ),

                    )
                    Box(modifier = Modifier
                        .fillMaxSize(),
                        contentAlignment = Alignment.Center){
                    AppUsageArcChart(modifier = Modifier
                        .padding(innerPadding),
                        appUsages = appUsages,
                        totalTimeInMinutes = appUsages.sumOf { it.durationMinutes })

                }
                }
            }
        }
    }
}

