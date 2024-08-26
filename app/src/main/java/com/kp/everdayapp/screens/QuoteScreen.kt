package com.kp.everdayapp.screens

import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.aghajari.compose.lazyswipecards.LazySwipeCards
import com.kp.everdayapp.R
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
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazySwipeCards(
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
            items(
                quote?.toMutableList() ?: mutableListOf(
                    QuoteData(
                        "The secret of getting ahead is getting started.",
                        "~ Mark Twain",
                        category = ""
                    )
                )
            ) {
                CardContent(it)
            }
        }
    }
}

@Composable
fun CardContent(quote: QuoteData) {

    val painter =
        rememberImagePainter(data = "https://api.api-ninjas.com/v1/randomimage?category=" + quote.category.lowercase())

    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painter,
                contentDescription = "Quote Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                alpha = 0.5f
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Image(
                        painter = painterResource(R.drawable.quote_icon),
                        contentDescription = "Quote Icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(start = 20.dp, top = 40.dp, bottom = 10.dp)
                            .height(70.dp),
                        alignment = Alignment.TopStart
                    )
                    Text(
                        text = if (quote?.quote.isNullOrBlank()) "Loading..." else quote?.quote!!,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        modifier = if (quote?.quote.isNullOrBlank()) Modifier
                            .padding(all = 20.dp)
                            .fillMaxWidth() else Modifier.padding(all = 20.dp)
                    )
                    Text(
                        text = quote?.author ?: "Author Name",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 40.dp, top = 10.dp),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(70.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE86043)),
                        contentPadding = PaddingValues(all = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "content description",
                            tint = Color.White,
                            modifier = Modifier.fillMaxSize(0.5F)
                        )
                    }
                }
            }
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