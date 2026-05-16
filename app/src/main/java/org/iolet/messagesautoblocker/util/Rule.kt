package org.iolet.messagesautoblocker.util

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "rules")
data class Rule(
    @PrimaryKey val id: Int,
    var rule: String,
)