package com.wise.code.room.sample.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserEntity>

    @Query("SELECT first_name, last_name FROM users")
    suspend fun getUsersName(): List<UserEntitySubset>

    @Query("SELECT * FROM users WHERE age > :minAge")
    suspend fun getUsersOlderThan(minAge: Int): List<UserEntity>
}