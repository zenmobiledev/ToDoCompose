package com.mobbelldev.todocompose.data.model

import androidx.compose.ui.graphics.Color
import com.mobbelldev.todocompose.ui.theme.HighPriorityColor
import com.mobbelldev.todocompose.ui.theme.LowPriorityColor
import com.mobbelldev.todocompose.ui.theme.MediumPriorityColor
import com.mobbelldev.todocompose.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}