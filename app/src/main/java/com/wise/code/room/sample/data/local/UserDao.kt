package com.wise.code.room.sample.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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

    @Query("SELECT * FROM users WHERE age > :minAge LIMIT 1")
    suspend fun getUsersOlderThan(minAge: Int): List<UserEntity>

    @Query("SELECT * FROM users WHERE age BETWEEN :minAge AND :maxAge")
    suspend fun getUsersBetween(minAge: Int, maxAge: Int): List<UserEntity>

    @Query("SELECT * FROM users WHERE first_name LIKE :searchQuery OR last_name LIKE :searchQuery")
    suspend fun findUserByName(searchQuery: String)

    @Query("SELECT * FROM users WHERE first_name LIKE '%' || :searchQuery || '%' OR last_name LIKE '%' || :searchQuery || '%'")
    suspend fun searchUserByName(searchQuery: String)

    @Query("SELECT * FROM users ORDER BY age ASC")
    suspend fun getUsersOrderedByAge(): List<UserEntity>

    @Query("SELECT * FROM users ORDER BY first_name ASC, last_name ASC")
    suspend fun getUsersOrderedByName(): List<UserEntity>

    @Query("SELECT * FROM Users WHERE country IN (:countries)")
    suspend fun findUsersByCountry(countries: List<String>): List<UserEntity>

    @Transaction
    @Query("SELECT * FROM Users")
    suspend fun getUsersWithPosts(): List<UserWithPosts>
}