package com.app.taskmanagement.domain.model

import androidx.compose.ui.graphics.Color

enum class CategoryType(val text: String, val color: Color) {
    DEVELOPMENT("Development", Color(0xFF7C2D2D)),
    MARKETING("Marketing", Color(0xFF837834)),
    DESIGN("Design", Color(0xFF2E7A4A)),
    RESEARCH("Research", Color(0xFF246457)),
    EVENT_PLANING("Event Planning", Color(0xFF3F2A77)),
    FINANCE("Finance", Color(0xFF822F94)),
    HUMAN_RESOURCES("Human Resources", Color(0xFF289CB1)),
    OPERATIONS("Operations", Color(0xFFAACA35)),
    SALES("Sales", Color(0xFFCA5A5A)),
    PRODUCT_DEVELOPMENT("Product Development", Color(0xFF284497)),
    CUSTOMER_SUPPORT("Customer Support", Color(0xFF1B555F)),
    LEGAL("Legal", Color(0xFFE276E1))
}

