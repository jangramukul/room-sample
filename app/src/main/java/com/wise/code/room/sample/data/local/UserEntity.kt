package com.wise.code.room.sample.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["first_name", "last_name"], unique = true),
        Index(value = ["age"]),
    ]
)
data class UserEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "country") val country: String,
    val phoneNumber: String, // Even without @ColumnInfo, This will be column in table
    @Ignore val online: String, // Ignore this as column
    @Embedded val address: Address, // Flatten the columns in this table from Address model
)

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey val id: Int,
    val content: String,
    @ColumnInfo("owner_user_id") val ownerUserId: Int,
)


// LEFT JOIN - Relationship - Returns all users but posts list can be empty
data class UserWithPosts(
    @Embedded val user: UserEntity,

    @Relation(
        parentColumn = "id", // UserEntity
        entityColumn = "ownerUserId" //PostEntity
    )
    val posts: List<PostEntity>
)

// Note: This is not an entity/table, This is just used for UserEntity as Embedded
data class Address(
    @ColumnInfo(name = "state_id") val stateId: Int
)