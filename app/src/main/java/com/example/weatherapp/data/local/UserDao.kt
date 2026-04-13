//package com.example.weatherapp.data.local
//
//import androidx.room.*
//
//@Dao
//interface UserDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(user: UserEntity)
//    @Query("SELECT * FROM users WHERE login = :login AND password = :password LIMIT 1")
//    suspend fun login(login: String, password: String): UserEntity?
//    @Query("SELECT * FROM users WHERE rememberMe = 1 LIMIT 1")
//    suspend fun getRememberedUser(): UserEntity?
//    @Query("UPDATE users SET lastCity = :city WHERE login = :login")
//    suspend fun updateCity(login: String, city: String)
//
//    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
//    suspend fun getUserByLogin(login: String): UserEntity?
//}