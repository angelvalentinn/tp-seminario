package com.example.trabajopractico

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface UserDao {

    @Query("SELECT * FROM user_entity")
    fun getAll(): List<User>

    @Query("SELECT * FROM user_entity WHERE email = :email")
    fun getByEmail(email: String): User

    @Query("SELECT * FROM user_entity WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): User?

    @Insert
    fun insert(user: User)

    @Query("DELETE FROM user_entity")
    fun deleteAllUsers()
}