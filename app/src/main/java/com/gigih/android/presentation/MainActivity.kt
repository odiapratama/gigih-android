package com.gigih.android.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.gigih.android.data.database.AppPreferences
import com.gigih.android.ui.theme.GigihAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: AppPreferences

    private val themeState by lazy { mutableStateOf(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) }

    override fun onResume() {
        super.onResume()
        themeState.value = preferences.themeMode
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GigihAndroidTheme(
                when (themeState.value) {
                    AppCompatDelegate.MODE_NIGHT_YES -> true
                    AppCompatDelegate.MODE_NIGHT_NO -> false
                    else -> isSystemInDarkTheme()
                }
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var text by rememberSaveable { mutableStateOf("Text") }
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        TextField(
            value = text,
            onValueChange = {
                text = it
            }
        )
        Button(onClick = {
            val intent = Intent(context, MenuActivity::class.java).apply {
                putExtra("data", text)
            }
            context.startActivity(intent)
        }) {
            Text(text = "Go to menu")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GigihAndroidTheme {
        MainView("Android")
    }
}