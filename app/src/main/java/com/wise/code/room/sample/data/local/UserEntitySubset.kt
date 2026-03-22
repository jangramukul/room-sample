package com.wise.code.room.sample.data.local

import androidx.room.ColumnInfo

data class UserEntitySubset(
    @ColumnInfo("first_name") val firstName: String,
    @ColumnInfo("last_name") val lastName: String,
)