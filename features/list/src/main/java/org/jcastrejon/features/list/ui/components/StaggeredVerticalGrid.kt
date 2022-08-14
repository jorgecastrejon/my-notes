package org.jcastrejon.features.list.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection.Ltr
import androidx.compose.ui.unit.dp

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    columns: Int,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    spaceBy: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        require(columns > 0) { "there should be at least a single column" }
        check(constraints.hasBoundedWidth) { "Unbounded width not supported" }

        val leftPadding = contentPadding.calculateLeftPadding(Ltr).roundToPx()
        val rightPadding = contentPadding.calculateRightPadding(Ltr).roundToPx()
        val topPadding = contentPadding.calculateTopPadding().roundToPx()
        val bottomPadding = contentPadding.calculateBottomPadding().roundToPx()
        val space = spaceBy.roundToPx()

        val availableWidth = constraints.maxWidth - leftPadding - rightPadding - (space * (columns - 1))

        val columnWidth = availableWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height

        val placeables = measurables.mapIndexed { index, measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            val extraSpace = if (colHeights[column] == 0) 0 else space
            colHeights[column] += extraSpace + placeable.height
            placeable
        }

        val height = (colHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight) + topPadding + bottomPadding

        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                val extraSpace = if (colY[column] == 0) 0 else space
                placeable.place(
                    x = leftPadding + (columnWidth * column) + (column * space),
                    y = topPadding + colY[column] + extraSpace
                )
                colY[column] += (placeable.height + extraSpace)
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}