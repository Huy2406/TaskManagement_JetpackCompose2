package com.app.taskmanagement.presentation.ui.screen.mytasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.taskmanagement.domain.model.Project
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CardItemProject(
    project: Project,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit,
    onCardClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val taskDone = project.tasks.filter { it.done }
    val progress = taskDone.size / project.tasks.size.toFloat()
    Card(
        onClick = { onCardClick() },
    ) {
        Column(
            modifier = Modifier
                .width(176.dp)
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = project.categoryType.color,
                    ),
                    shape = RoundedCornerShape(percent = 18)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = project.categoryType.text,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

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
                    offset = DpOffset(40.dp, -5.dp)
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

            Text(
                text = project.title,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = project.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomProgressIndicator(progress = progress, progressColor = project.categoryType.color)

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Text(
                    text = convertLocalDateTimeToDate(project.dueDate),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${taskDone.size}/${project.tasks.size} Task", fontSize = 12.sp)
            }
        }
    }
}

fun convertLocalDateTimeToDate(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return date.format(formatter)
}