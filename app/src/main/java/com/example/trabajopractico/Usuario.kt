package com.example.trabajopractico

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class User(
    @ColumnInfo(name="email")var email: String,
    @ColumnInfo(name="password")var password: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}