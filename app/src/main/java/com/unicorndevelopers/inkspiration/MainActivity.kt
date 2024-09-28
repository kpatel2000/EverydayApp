package com.unicorndevelopers.inkspiration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.unicorndevelopers.inkspiration.screens.QuoteScreen
import com.unicorndevelopers.inkspiration.ui.theme.EverdayAppTheme
import com.unicorndevelopers.inkspiration.viewmodel.QuoteViewModel

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
                            title = {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_app_name),
                                        contentDescription = "Logo image",
                                        modifier = Modifier.height(40.dp),
                                        contentScale = ContentScale.FillHeight
                                    )
                                    Text(
                                        "nkspiration",
                                        fontFamily = FontFamily(Font(R.font.kalam)),
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0B122E),
                                        fontSize = 30.sp,
                                        modifier = Modifier.align(Alignment.Bottom)
                                    )
                                }
                                    },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent,
                                titleContentColor = Color(0xFF0B122E)
                            ),
//                            modifier = Modifier.background(brush = Brush.radialGradient(gradient))
                            modifier = Modifier.background(color = Color.White)

                        )
                    }
                ){ padding ->
                    QuoteScreen(quoteViewModel, padding)
                }
            }
        }
    }
}
