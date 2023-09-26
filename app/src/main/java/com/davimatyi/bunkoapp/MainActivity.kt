package com.davimatyi.bunkoapp

import android.content.Context
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davimatyi.bunkoapp.R.drawable.bunko
import com.davimatyi.bunkoapp.ui.theme.BunkóAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BunkóAppTheme {
                val context = applicationContext
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Club(context)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClubPreview() {
    Club(LocalContext.current)
}

@Composable
fun Club(context: Context) {
    var isRotated by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val rotationState = animateFloatAsState(
        targetValue = if (isRotated) -30f else 0f,
        animationSpec = tween(durationMillis = 200), label = ""
    )

    val rotationModifier = Modifier.graphicsLayer(
        rotationZ = rotationState.value
    )



    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                isRotated = true
                coroutineScope.launch {
                    val mediaPlayer = MediaPlayer.create(context, R.raw.bonk)
                    mediaPlayer.start()
                    delay(200) // Delay to allow the animation to complete
                    isRotated = false
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.bunko), // Replace with your image resource
            contentDescription = "bunko",
            modifier = rotationModifier.fillMaxSize()
        )
    }
}
