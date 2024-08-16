package com.app.taskmanagement.presentation.ui.screen.projectdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.taskmanagement.domain.model.Task

@Composable
fun ItemTask(
    task: Task,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
) {
    var checked by remember { mutableStateOf(task.done) }
    var menuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(task.done) {
        checked = task.done
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 64.dp),
                    text = task.description,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Checkbox(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                checked = checked,
                onCheckedChange = {
                    checked = it
                    onCheckedChange(it)
                })

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        menuExpanded = !menuExpanded
                    },
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = null
            )

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                offset = DpOffset(210.dp, -5.dp)
            ) {
                val list = listOf("Edit", "Delete")
                list.forEach {
                    DropdownMenuItem(text = { Text(text = it) }, onClick = {
                        if (it == "Edit") {
                            onClickEdit()
                            menuExpanded = false
                        } else {
                            onClickDelete()
                            menuExpanded = false
                        }
                    })
                }

            }
        }
    }
}