package com.example.segmentedcircularchart


import android.R.attr.fontWeight
import android.R.attr.label
import android.R.attr.radius
import android.R.attr.strokeWidth
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin


data class AppUsage(
    val name:String,
    val durationMinutes: Int,
    val color: Color,
    val icon: ImageVector,
)

@Composable
fun AppUsageArcChart(
    modifier: Modifier = Modifier,
    appUsages:List<AppUsage>,
    totalTimeInMinutes:Int
){
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotationAngle = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 100000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation_angle"
    )
    Box(
        modifier = modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            rotate(rotationAngle.value) {
                val strokeWidth = 30.dp.toPx()
                val radius = (size.minDimension - strokeWidth) / 2
                val center = Offset(size.width / 2, size.height / 2)

                var currentAngle = -90f
                val totalTimeInMinutes = appUsages.sumOf { it.durationMinutes }


                appUsages.forEach { appUsage ->
                    val percentage = appUsage.durationMinutes.toFloat() / totalTimeInMinutes
                    val sweepAngle = 360f * percentage
                    drawArc(
                        color = appUsage.color,
                        startAngle = currentAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round
                        ),
                        size = Size(radius * 2, radius * 2),
                        topLeft = Offset(
                            center.x - radius,
                            center.y - radius
                        )
                    )
                    currentAngle += sweepAngle
                }
            }}
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$totalTimeInMinutes mins",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Today time",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            var cumulativeAngle = -90f

            appUsages.forEach { appUsage ->
                val totalTime = appUsages.sumOf { it.durationMinutes }
                val percentage = appUsage.durationMinutes.toFloat() / totalTime
                val sweepAngle = 360f * percentage
                val middleAngle = cumulativeAngle + (sweepAngle / 2) + rotationAngle.value
                AppIcon(
                    appUsage = appUsage,
                    angle = middleAngle
                )
                cumulativeAngle += sweepAngle
            }

        }


}

@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    appUsage: AppUsage,
    angle: Float
){

    val radius = 120.dp

    val offsetX = radius * cos(Math.toRadians(angle.toDouble())).toFloat()
    val offsetY = radius * sin(Math.toRadians(angle.toDouble())).toFloat()

    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .offset(offsetX,offsetY)
                .size(80.dp)
                .padding(24.dp)
                .background(
                    color = appUsage.color,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = appUsage.icon,
                contentDescription = appUsage.name,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )

        }

    }

}