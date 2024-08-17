package com.app.taskmanagement.presentation.ui.screen.mytasks

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AlertDeleteDialog(
    isShowDialog: MutableState<Boolean>,
    onConfirm: () -> Unit
) {
    if (isShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                isShowDialog.value = false
            },
            title = { Text(text = "Delete Project") },
            text = { Text(text = "Do you want to delete this project?") },
            confirmButton = {
                Row {
                    Button(
                        onClick = {
                            isShowDialog.value = false
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            isShowDialog.value = false
                            onConfirm()
                        }
                    ) {
                        Text(
                            text = "Confirm",
                            color = Color.White
                        )
                    }
                }
            }
        )
    }
}