package com.kp.everdayapp.screens

import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aghajari.compose.lazyswipecards.LazySwipeCards
import com.kp.everdayapp.models.QuoteData
import com.kp.everdayapp.ui.theme.EverdayAppTheme
import com.kp.everdayapp.viewmodel.QuoteViewModel

@Composable
fun QuoteScreen(quoteViewModel: QuoteViewModel) {

    val quote by quoteViewModel.quoteLiveData.observeAsState(initial = null)

    LaunchedEffect(key1 = Unit) {
        quoteViewModel.getQuote()
    }
    val gradient = listOf(Color.Yellow, Color.Green)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(gradient)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(
//            text = quote?.category?.uppercase() ?: "Category Name",
//            fontSize = 18.sp,
//            color = Color.Black,
//            fontWeight = FontWeight.Bold
//        )

        LazySwipeCards(
            cardModifier =  Modifier,
            cardShape = RoundedCornerShape(16.dp),
            cardShadowElevation = 4.dp,
            visibleItemCount = 4,
            rotateDegree = 15f,
            translateSize = 24.dp,
            animationSpec = SpringSpec(),
            swipeThreshold = 0.5f,
            scaleFactor = ScaleFactor(
                scaleX = 0.1f,
                scaleY = 0.1f
            ),
            contentPadding = PaddingValues(
                vertical = 24.dp * 4, // visibleItemCount
                horizontal = 24.dp
            )
        ) {
            items(quote?.toMutableList()?: mutableListOf(QuoteData("Loading...","name", category = ""))){
                CardContent(it)
            }
        }

        }
    }

@Composable
fun CardContent (quote : QuoteData){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (quote?.quote.isNullOrBlank()) "Loading..." else quote?.quote!!,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = if (quote?.quote.isNullOrBlank()) Modifier
                    .padding(all = 10.dp)
                    .fillMaxWidth() else Modifier.padding(all = 10.dp)
            )

            Text(text = quote?.author ?: "Author Name",
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp, top = 10.dp),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    EverdayAppTheme {
        val viewModel = QuoteViewModel()
        QuoteScreen(viewModel)
    }
}