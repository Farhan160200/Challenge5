package com.farhanfarkaann.challenge5.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.mycomposeapp.ui.theme.MyTheme
import com.skydoves.landscapist.glide.GlideImage
import com.farhanfarkaann.challenge5.ui.fragment.HomeFragment.Companion.BACKDROP
import com.farhanfarkaann.challenge5.ui.fragment.HomeFragment.Companion.POPULARITY
import com.farhanfarkaann.challenge5.ui.fragment.HomeFragment.Companion.ID
import com.farhanfarkaann.challenge5.ui.fragment.HomeFragment.Companion.ID2
import com.farhanfarkaann.challenge5.ui.fragment.HomeFragment.Companion.ID3
import com.farhanfarkaann.challenge5.ui.fragment.HomeFragment.Companion.OVERVIEW
import com.farhanfarkaann.challenge5.ui.fragment.HomeFragment.Companion.TITLE


class DetailCompose : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            val bundle = arguments
            val idTopRated = bundle?.getInt(ID)
            val idPopular = bundle?.getInt(ID2)
            val idUpComing = bundle?.getInt(ID3)
            val backDrop = bundle?.getString(BACKDROP)
            val overView = bundle?.getString(OVERVIEW)
            val title = bundle?.getString(TITLE)
            val popularity = bundle?.getDouble(POPULARITY)
            setContent {
                MyTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    )
                    {
                        ImageWithBackground(
                            painter = painterResource(id = R.drawable.wallpapers),
                            backgroundDrawableResId = R.drawable.wallpapers,
                            contentDescription = "",
                            modifier = Modifier
                                .height(2580.dp)
                                .width(2960.dp)
                                .padding(0.dp),
                        )


                        Column {
                            Detail(
                                title.toString(),
                                overView.toString(),
                                backDrop.toString(),
                                popularity.toString()
                            )
                        }

                    }

                }
            }
        }
    }


    @Composable
    fun Detail(
        title: String, overView: String, backDrop: String,
        popularity: String
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Spacer(modifier = Modifier.height(42.dp))
            GlideImage(
                imageModel = "https://image.tmdb.org/t/p/w500"+ backDrop,
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(250.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 16.dp)

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Popularity "+popularity,
                textAlign = TextAlign.Left,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(horizontal = 16.dp)

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = overView,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

        }
    }
    @Composable
    fun ImageWithBackground(
        painter: Painter,
        @DrawableRes backgroundDrawableResId: Int,
        contentDescription: String?,
        modifier: Modifier = Modifier,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.FillBounds,
        alpha: Float = DefaultAlpha,
        colorFilter: ColorFilter? = null
    ) {
        Box(
            modifier = modifier
        ) {
            Image(
                painter = painterResource(backgroundDrawableResId),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
            )
            Image(
                painter = painter,
                contentDescription = contentDescription,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                modifier = Modifier
                    .matchParentSize()
            )
        }
    }

/////////////////////////////////


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {

        MyTheme {
            Column {
                ImageWithBackground(
                    painter = painterResource(id = R.drawable.wallpapers),
                    backgroundDrawableResId = R.drawable.wallpapers,
                    contentDescription = "",
                    modifier = Modifier
                        .height(2580.dp)
                        .width(2960.dp)
                        .padding(0.dp),
                )
                Detail(
                    "ini JUDUL",
                   "menceritakan tentnang akakskkskaksaksakasaksakskdaskdaksdkasdkas",
                    "/kXfqcdQKsToO0OUXHcrrNCHDBzO.jpg",
                    "ini genre , genre, genre , genre",
                )
            }
        }
    }
}

