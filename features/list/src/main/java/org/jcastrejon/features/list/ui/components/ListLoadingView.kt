package org.jcastrejon.features.list.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun ListLoadingView(
    modifier: Modifier = Modifier,
) {
    StaggeredVerticalGrid(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        columns = 2,
        contentPadding = PaddingValues(16.dp),
        spaceBy = 12.dp
    ) {
        LoadingItem(height = 125.dp)
        LoadingItem(height = 180.dp)
        LoadingItem(height = 125.dp)
        LoadingItem(height = 180.dp)
        LoadingItem(height = 125.dp)
    }
}

@Composable
fun LoadingItem(
    modifier: Modifier = Modifier,
    height: Dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .height(height)
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(),
            )
    )
}