
package tw.edu.pu.csim.tcyang.canvax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import tw.edu.pu.csim.tcyang.canvax.ui.theme.CanvaxTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CanvaxTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CanvasScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TestCanvas(modifier: Modifier = Modifier) {

    val path = Path().apply {
        moveTo(50f, 100f)
        lineTo(200f, 500f)
        lineTo(450f, 400f)
        close()
    }

    val minions = ImageBitmap.imageResource(
        id = R.drawable.minions
    )

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
            style = Stroke(width = 5f)
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

        drawPath(
            path = path,
            color = Color.Green,
            style = Stroke(width = 10f)
        )
    }
}

@Composable
fun CanvasScreen(modifier: Modifier = Modifier) {

    var CanvasWidth by remember {
        mutableStateOf(value = 0f)
    }

    var CanvasHeight by remember {
        mutableStateOf(value = 0f)
    }

    var minionsX by remember {
        mutableStateOf(value = 0f)
    }

    var minionsY by remember {
        mutableStateOf(value = 0f)
    }

    var gameOver by remember {
        mutableStateOf(value = false)
    }

    // 載入圖片（小小兵）
    val minionsImage: ImageBitmap =
        ImageBitmap.imageResource(
            id = R.drawable.minions
        )

    // 控制小小兵移動
    LaunchedEffect(key1 = gameOver) {
        while (!gameOver) {
            delay(timeMillis = 100L)

            minionsX += 5
            minionsY += 10

            // 小小兵完全移出畫布時，回到左上角
            if (
                minionsX >= CanvasWidth ||
                minionsY >= CanvasHeight
            ) {
                minionsX = 0f
                minionsY = 0f
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    CanvasWidth = size.width.toFloat()
                    CanvasHeight = size.height.toFloat()
                }
        ) {
            translate(
                left = minionsX,
                top = minionsY
            ) {
                drawImage(minionsImage)
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("畫布資訊 $CanvasWidth, 高度 $CanvasHeight")

            Button(
                onClick = {
                    if (gameOver) {
                        minionsX = 0f
                        minionsY = 0f
                    }

                    gameOver = !gameOver
                }
            ) {
                if (gameOver) {
                    Text("重新開始")
                } else {
                    Text("停止遊戲")
                }
            }
        }
    }
}
