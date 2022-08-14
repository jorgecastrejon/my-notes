package org.jcastrejon.features.list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jcastrejon.notes.Note

@Composable
fun ListView(
    modifier: Modifier = Modifier,
    notes: List<Note> = emptyList()
) {
    StaggeredVerticalGrid(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        columns = 2,
        contentPadding = PaddingValues(16.dp),
        spaceBy = 12.dp
    ) {
        notes.forEach { note ->
            NoteView(note = note)
        }
    }
}

@Composable
fun NoteView(
    modifier: Modifier = Modifier,
    note: Note
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(note.color))
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
}