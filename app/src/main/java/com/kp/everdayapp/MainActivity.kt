package com.kp.everdayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kp.everdayapp.screens.QuoteScreen
import com.kp.everdayapp.ui.theme.EverdayAppTheme
import com.kp.everdayapp.viewmodel.QuoteViewModel

class MainActivity : ComponentActivity() {
    private val quoteViewModel: QuoteViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Enable Firebase Crashlytics */
        FirebaseApp.initializeApp(this)
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCrashlyticsCollectionEnabled(true)
        enableEdgeToEdge()
        setContent {
            EverdayAppTheme {
                val gradient = listOf(Color(0xFF0B122E), Color(0xFF0A132C))
                Scaffold (
                    topBar = {
                        TopAppBar(
                            title = { Text("Inkspiration", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent,
                                titleContentColor = Color.White
                            ),
                            modifier = Modifier.background(brush = Brush.radialGradient(gradient))
                        )
                    }
                ){ padding ->
                    QuoteScreen(quoteViewModel, padding)
                }
            }
        }
    }
}
