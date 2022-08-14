package org.jcastrejon.features.list.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jcastrejon.features.list.R
import org.jcastrejon.notes.Note

@Composable
fun ListView(
    modifier: Modifier = Modifier,
    notes: List<Note> = emptyList(),
    editMode: Boolean = false,
    selectedNotes: List<Int> = emptyList(),
    onNoteClick: (Int) -> Unit,
) {
    StaggeredVerticalGrid(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        columns = 2,
        contentPadding = PaddingValues(16.dp),
        spaceBy = 12.dp
    ) {
        notes.forEach { note ->
            NoteView(
                note = note,
                editMode = editMode,
                isSelected = note.id in selectedNotes,
                onNoteClick = { onNoteClick(note.id) }
            )
        }
    }
}

@Composable
fun NoteView(
    modifier: Modifier = Modifier,
    note: Note,
    editMode: Boolean = false,
    isSelected: Boolean = false,
    onNoteClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(note.color))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = onNoteClick
            ),
        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = note.title,
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
            )
            Text(
                text = note.createdDate.toReadableFormat(showYear = true),
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            )
        }
        AnimatedVisibility(visible = editMode) {
            val image = AnimatedImageVector.animatedVectorResource(R.drawable.avd_bookmark)
            Image(
                painter = rememberAnimatedVectorPainter(
                    animatedImageVector = image,
                    atEnd = isSelected
                ),
                contentDescription = "Selected Mode",
            )
        }
    }
}