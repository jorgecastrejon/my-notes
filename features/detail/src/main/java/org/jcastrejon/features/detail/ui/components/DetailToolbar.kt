package org.jcastrejon.features.detail.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardBackspace
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun DetailToolbar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ToolbarIconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardBackspace,
                contentDescription = "back",
                tint = MaterialTheme.colors.onBackground
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ToolbarIconButton(
            onClick = onRemoveClick
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "delete",
                tint = MaterialTheme.colors.onBackground
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        ToolbarIconButton(
            onClick = onEditClick
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "edit",
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}
@Composable
fun ToolbarIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(vertical = 8.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.onBackground.copy(alpha = 0.1f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
        content = content
    )
}
