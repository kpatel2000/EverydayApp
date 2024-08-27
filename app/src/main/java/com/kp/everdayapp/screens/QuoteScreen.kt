package com.kp.everdayapp.screens

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (quote?.isNotEmpty() == true)
            SwipeCard(quoteViewModel = quoteViewModel, quote = quote)
        else
            SwipeCard(
                quoteViewModel = quoteViewModel,
                quote = listOf(
                    QuoteData(
                        "Patience is not the ability to wait, but the ability to keep a good attitude while waiting.",
                        "Joyce Meyer",
                        "Nature"
                    )
                )
            )
    }
}

@Composable
fun SwipeCard(quoteViewModel: QuoteViewModel, quote: List<QuoteData>?){
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
            vertical = 24.dp * 3,
            horizontal = 24.dp
        )
    ) {
        onSwiped { item, direction ->
            quoteViewModel.getQuote()
            quoteViewModel.resetLiveData()
        }
        items(
            quote?.toMutableList() ?: mutableListOf(
                QuoteData(
                    "Loading...",
                    "Author",
                    category = ""
                )
            )
        ) {
            CardContent(it, quoteViewModel)
        }
    }
}
@OptIn(ExperimentalCoilApi::class)
@Composable
fun CardContent(quote: QuoteData, quoteViewModel: QuoteViewModel) {

    val image by quoteViewModel.image.observeAsState()

    val painter: Painter  = painterResource(id = R.drawable.nature)

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
            if(image != null){
                val imageBytes = Base64.decode(image, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Image(
                    painter = rememberImagePainter(data = decodedImage),
                    contentDescription = "Quote Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    alpha = 0.5f
                )
            }else {
                Image(
                    painter = painter,
                    contentDescription = "Quote Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    alpha = 0.5f
                )
            }
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
                            .height(30.dp),
                        alignment = Alignment.TopStart
                    )
                    Text(
                        text = if (quote?.quote.isNullOrBlank()) "Loading..." else quote?.quote!!,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        maxLines = 12,
                        overflow = TextOverflow.Ellipsis,
                        modifier = if (quote?.quote.isNullOrBlank()) Modifier
                            .padding(all = 20.dp)
                            .fillMaxWidth() else Modifier.padding(all = 20.dp)
                    )
                    Text(
                        text = "~ ${quote?.author}" ?: "Author Name",
                        fontSize = 16.sp,
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
                        modifier = Modifier.size(50.dp),
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