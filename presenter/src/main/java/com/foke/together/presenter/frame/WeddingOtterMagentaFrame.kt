package com.foke.together.presenter.frame

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.foke.together.domain.interactor.entity.FramePosition
import com.foke.together.presenter.R
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.theme.highContrastDarkColorScheme
import com.foke.together.presenter.theme.mediumContrastLightColorScheme
import com.foke.together.presenter.theme.weddingOtterMagenta
import com.foke.together.util.AppPolicy
import com.foke.together.util.TimeUtil

@Composable
fun WeddingOtterMagentaFrame(
    // TODO: Need to refactoring. separate frame design with application theme
    designColorScheme: ColorScheme = mediumContrastLightColorScheme,
    cameraImageUrlList : List<Uri>? = null,
    position: FramePosition? = null
) {
    ConstraintLayout(
        modifier = Modifier
            .aspectRatio(ratio = 0.3333f)
            .background(color = weddingOtterMagenta)
            .border(1.dp, designColorScheme.inverseSurface)
            .padding(
                start = position.let {
                    when(it){
                        FramePosition.LEFT -> 20.dp
                        FramePosition.RIGHT -> 0.dp
                        null -> 10.dp
                    }
                },
                end = position.let {
                    when(it){
                        FramePosition.LEFT -> 0.dp
                        FramePosition.RIGHT -> 20.dp
                        null -> 10.dp
                    }
                },
                top = 40.dp
            )
    ) {
        val (cameraColumn, decorateRow) = createRefs()
        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 15.dp),
            modifier = Modifier
                .constrainAs(cameraColumn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(decorateRow.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .wrapContentSize()
        ) {
            items(AppPolicy.CAPTURE_COUNT){
                //TODO: add camera image
                // change Box -> ImageView
                Box(
                    modifier = Modifier
                        .aspectRatio(1.5f)
                        .background(color = Color.White)
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(cameraImageUrlList?.get(it))
                            .build(),
                        contentDescription = "",
                        modifier = Modifier.fillParentMaxSize()

                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .constrainAs(decorateRow){
                    top.linkTo(cameraColumn.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.wedding_otter_magenta),
                contentDescription = "for decorate",
                modifier = Modifier.weight(1f)
            )
            Text(
                text = TimeUtil.getCurrentDisplayTime(),
                modifier = Modifier.weight(1f),
                color = Color.White,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultFrame() {
    FourCutTogetherTheme {
        WeddingOtterMagentaFrame(
            designColorScheme = highContrastDarkColorScheme
        )
    }
}