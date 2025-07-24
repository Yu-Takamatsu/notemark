package com.yu.pl.app.challeng.notemark.features.presentation.note.editnote.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteEditMode

@Composable
fun EdiModeSelector(
    mode: NoteEditMode,
    onChangeMode: (mode: NoteEditMode) -> Unit,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .size(height = 52.dp, width = 100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(4.dp)
    ) {


        val segmentedButtonModifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
        val segmentedButtonShape = RoundedCornerShape(1.dp)
        val segmentedButtonColors = SegmentedButtonDefaults.colors(
            activeContentColor = MaterialTheme.colorScheme.primary,
            activeContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            activeBorderColor = Color.Transparent,
            inactiveBorderColor = Color.Transparent,
            inactiveContainerColor = Color.Transparent
        )

        SegmentedButton(
            onClick = {
                onChangeMode(
                    if (mode == NoteEditMode.Edit) NoteEditMode.View else NoteEditMode.Edit
                )
            },
            selected = mode == NoteEditMode.Edit,
            shape = segmentedButtonShape,
            modifier = segmentedButtonModifier,
            enabled = true,
            colors = segmentedButtonColors,
            border = SegmentedButtonDefaults.borderStroke(color = Color.Transparent, width = 0.dp),
            icon = {},//Needed to remove default check mark
            label = {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_edit_mode),
                    contentDescription = if (mode == NoteEditMode.Edit) stringResource(R.string.edit_note_to_view_mode) else stringResource(
                        R.string.edit_note_to_edit_mode
                    ),
                    tint = if (mode == NoteEditMode.Edit) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        )
        Spacer(modifier = Modifier.size(4.dp))
        SegmentedButton(
            onClick = {
                onChangeMode(
                    if (mode == NoteEditMode.Reader) NoteEditMode.View else NoteEditMode.Reader
                )
            },
            selected = mode == NoteEditMode.Reader,
            shape = segmentedButtonShape,
            modifier = segmentedButtonModifier,
            enabled = true,
            icon = {},//Needed to remove default check mark
            colors = segmentedButtonColors,
            label = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_reader_mode),
                    contentDescription = if (mode == NoteEditMode.Reader) stringResource(R.string.edit_note_to_view_mode) else stringResource(
                        R.string.edit_note_to_reader_mode
                    ),
                    tint = if (mode == NoteEditMode.Reader) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        )
    }
}