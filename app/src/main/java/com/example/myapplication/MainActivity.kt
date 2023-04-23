package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.json.JSONObject

const val HELLO_ANDROID = "Hello Android!"
const val ERROR = "Error"
const val URL_FOR_TEST = "https://demo2803075.mockable.io/flamingo_test"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(baseContext)
                }
            }
        }
    }

}


private fun sendGet(baseContext: Context, updateName: (String) -> Unit) {
    val queue = Volley.newRequestQueue(baseContext)

    // Request a string response from the provided URL.
    val stringRequest = StringRequest(
        Request.Method.GET, URL_FOR_TEST,
        { response ->
            // Display the first 500 characters of the response string.
            response.toString()
            val obj = JSONObject(response)
            val newName: String = obj.optString("name")
            updateName(newName)
        },
        { updateName(ERROR) })

    // Add the request to the RequestQueue.
    queue.add(stringRequest)
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier) {
    Text(
        modifier = modifier,
        text = name,
        fontSize = TextUnit(32.dp.value, TextUnitType.Sp)
    )
}

@Composable
private fun MainScreen(baseContext: Context) {
    var name by remember { mutableStateOf(HELLO_ANDROID) }
    var isLoading by remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .align(Alignment.Center)
        ) {
            Greeting(
                name, modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(40.dp))

            Text(text = "Get Request", modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .background(Color.Green.copy(alpha = 0.5f))
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() } // This is mandatory
                ) {
                    isLoading = true
                    sendGet(baseContext) { newName ->
                        isLoading = false
                        name = newName
                    }
                },
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Reset", modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .background(Color.Blue.copy(alpha = 0.5f))
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() } // This is mandatory
                ) {
                    name = HELLO_ANDROID
                },
                color = Color.White
            )
        }
        if (isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xff, 0xff, 0xff, 0xb5), RectangleShape)
            ) {
                CircularProgressIndicator(
                    color = Color(0x42, 0x92, 0x3C, 0xB5),
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        }
    }
}
