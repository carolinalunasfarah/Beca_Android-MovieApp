import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beca_android_finalproject.R
import com.example.beca_android_finalproject.ui.theme.OnPrimary
import com.example.beca_android_finalproject.ui.theme.Primary
import com.example.beca_android_finalproject.ui.theme.Surface
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(onNavigate: () -> Unit) {
    val isVisible = remember { mutableStateOf(false) }

    val courgetteFont = FontFamily (
        Font(R.font.courgette_regular)
    )

    LaunchedEffect(Unit) {
        isVisible.value = true
        delay(3000)
        onNavigate()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Surface,
                        Primary
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = isVisible.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.clapperboard),
                    contentDescription = "Logo de ReelScout",
                    modifier = Modifier.size(120.dp)
                )
            }
            Text(
                text = "Welcome to ReelScout",
                style = MaterialTheme.typography.headlineLarge,
                color = OnPrimary,
                modifier = Modifier.padding(top = 16.dp),
                fontFamily = courgetteFont,
            )
            Text(
                text = "Discover your next favorite movie!",
                style = MaterialTheme.typography.bodyLarge,
                color = OnPrimary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen { }
}
