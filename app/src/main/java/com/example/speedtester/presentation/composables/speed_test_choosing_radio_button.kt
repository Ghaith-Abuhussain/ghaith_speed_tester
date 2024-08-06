package com.example.speedtester.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// This is the composable responsible of showing the Radio buttons that chooses between the Speed Test Type (Upload, Download, Both)
@Composable
fun SpeedTestChoosingRadioButton(selectedOption: String, options: List<String>, onClick: (String) -> Unit) {

    Row(Modifier.padding(16.dp)) {
        options.forEach { option ->
            val isSelected = option == selectedOption

            Column(
                Modifier
                    .wrapContentSize()
                    .selectable(
                        selected = isSelected,
                        onClick = { onClick(option) }
                    ).padding(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = {
                        onClick(option)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option, style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary))
            }
        }
    }
}