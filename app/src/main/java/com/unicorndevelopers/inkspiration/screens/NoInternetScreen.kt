package com.unicorndevelopers.inkspiration.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unicorndevelopers.inkspiration.R


@Composable
fun NoInternetScreen(paddingValues: PaddingValues) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.no_internet),
            contentDescription = "No Internet Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .height(280.dp)
        )
        Text(
            text = "Ooops!",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "There is no internet connection. Please check your internet connection and try again.",
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .align(Alignment.CenterHorizontally)
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(top = 40.dp),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE86043)),
        ) {
            Text(
                text = "Try Again",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewScreen() {
    val paddingValues = PaddingValues(all = 10.dp)
    NoInternetScreen(paddingValues)

}