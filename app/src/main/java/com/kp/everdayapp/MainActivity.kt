package com.kp.everdayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kp.everdayapp.screens.QuoteScreen
import com.kp.everdayapp.ui.theme.EverdayAppTheme
import com.kp.everdayapp.viewmodel.QuoteViewModel

class MainActivity : ComponentActivity() {
    private val quoteViewModel: QuoteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Enable Firebase Crashlytics */
        FirebaseApp.initializeApp(this)
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCrashlyticsCollectionEnabled(true)
        enableEdgeToEdge()
        setContent {
            EverdayAppTheme {
                QuoteScreen(quoteViewModel)
            }
        }
    }
}
