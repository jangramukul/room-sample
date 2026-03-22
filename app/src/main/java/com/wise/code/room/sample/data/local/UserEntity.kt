package com.wise.code.room.sample.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
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


/**
 * Foreign Key means Enforces Constraint -
 * 1) owner_user_id from PostEntity must exist in UserEntity
 * 2) Can't insert Post without user
 * 3) When user is delected, It deletes the Posts too (ForeignKey.CASCADE)
 */
@Entity(
    tableName = "post",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"], // UserEntity
            childColumns = ["owner_user_id"], //PostEntity
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index(value = ["owner_user_id"])] // Add Index here for performance as ForeignKey is added
)
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