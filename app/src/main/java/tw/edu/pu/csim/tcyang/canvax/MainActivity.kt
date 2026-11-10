package tw.edu.pu.csim.tcyang.canvax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tw.edu.pu.csim.tcyang.canvax.ui.theme.CanvaxTheme
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvaxTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TestCanvas(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TestCanvas(modifier: Modifier = Modifier) {
    val minions = ImageBitmap.imageResource(id = R.drawable.minions)
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        drawCircle(
            color = Color.Magenta,
            radius = 100f,
            center = Offset(100f, 100f)
        )


        drawRect(
            color = Color.Blue,
            topLeft = Offset(300f, 200f),
            size = androidx.compose.ui.geometry.Size(200f, 200f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5f)
        )


        drawLine(
            color = Color.Red,
            start = Offset(200f, 800f),
            end = Offset(size.width, size.height),
            strokeWidth = 10f
        )

        drawImage(
            image = minions,
            topLeft = Offset(
                (size.width - minions.width) / 2f,
                (size.height - minions.height) / 2f
            )
        )
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CanvaxTheme {
        Greeting("Android")
    }
}