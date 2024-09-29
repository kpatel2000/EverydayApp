package com.unicorndevelopers.inkspiration.screens

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build.VERSION.SDK_INT
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.aghajari.compose.lazyswipecards.LazySwipeCards
import com.unicorndevelopers.inkspiration.R
import com.unicorndevelopers.inkspiration.models.Quote
import com.unicorndevelopers.inkspiration.models.QuoteData
import com.unicorndevelopers.inkspiration.ui.theme.EverdayAppTheme
import com.unicorndevelopers.inkspiration.viewmodel.QuoteViewModel
import kotlin.text.Typography.quote

@Composable
fun QuoteScreen(quoteViewModel: QuoteViewModel, paddingValues: PaddingValues) {

    val quote by quoteViewModel.quoteLiveData.observeAsState(initial = null)

    LaunchedEffect(key1 = Unit) {
        quoteViewModel.getQuote()
    }
    if (quote != null && quote?.networkIssue == true){
        NoInternetScreen(paddingValues) {
            quoteViewModel.resetLiveData()
            quoteViewModel.getQuote()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (quote?.quote?.isNotEmpty() == true)
                SwipeCard(quoteViewModel = quoteViewModel, quote = quote)
            else
                SwipeCard(
                    quoteViewModel = quoteViewModel,
                    quote = null
                )
        }
    }
}

@Composable
fun SwipeCard(quoteViewModel: QuoteViewModel, quote: Quote?){
    if(quote == null) {
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
                vertical = 20.dp * 2,
                horizontal = 24.dp
            )
        ) {
            onSwiped { item, direction ->
                quoteViewModel.getQuote()
                quoteViewModel.resetLiveData()
            }
            itemsIndexed(mutableListOf(null)) { index, quoteData ->
                CardContent(quoteData, null)
            }
        }
    }else {
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
                vertical = 20.dp * 2,
                horizontal = 24.dp
            )
        ) {
            onSwiped { item, direction ->
                quoteViewModel.getQuote()
                quoteViewModel.resetLiveData()
            }
            itemsIndexed(quote.quote?.toMutableList() ?: mutableListOf(null)) { index, quoteData ->
                var image: String? = null
                if(index != 1){
                    image = quote.imageUrl?.get(index = index)
                }
                CardContent(quoteData, image)
            }
        }
    }
}

@Composable
fun CardContent(quote: QuoteData?, image: String?) {
    var shareQuote by remember { mutableStateOf(false) }
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
            //Showing Loader in case quote is not available
            if(quote == null) {
                val imageLoader = ImageLoader.Builder(LocalContext.current)
                    .components {
                        if (SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()
                Column(
                    modifier = Modifier.fillMaxSize().background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = rememberAsyncImagePainter(
                            R.drawable.quote_feather_loader,
                            imageLoader
                        ),
                        contentDescription = "Quote Background",
                        modifier = Modifier.size(width = 200.dp, height = 200.dp)
                            .padding(start = 20.dp, bottom = 20.dp),
                    )
                }

            }else {
                val painter: Painter  = painterResource(id = R.drawable.nature)

                if (!image.isNullOrEmpty()) {
                    val imageBytes = Base64.decode(image, Base64.DEFAULT)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    Image(
                        painter = rememberAsyncImagePainter(model = decodedImage),
                        contentDescription = "Quote Background",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        alpha = 0.5f
                    )
                } else {
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
                        /*Text(
                        text = "~ ${quote?.author}" ?: "Author Name",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 40.dp, top = 10.dp),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Medium
                    )*/
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 40.dp, top = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Button(
                            onClick = {
                                shareQuote = true
                            },
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
    val shareIntent = remember {
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, quote?.quote)
            type = "text/plain"
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {}
    )
    if (shareQuote) {
        launcher.launch(Intent.createChooser(shareIntent, "Share via"))
        shareQuote = false
    }
}


@Preview(showSystemUi = true)
@Composable
fun Preview() {
    EverdayAppTheme {
        val viewModel = QuoteViewModel()
        val paddingValues = PaddingValues(all = 10.dp)
        QuoteScreen(viewModel, paddingValues)
    }
}