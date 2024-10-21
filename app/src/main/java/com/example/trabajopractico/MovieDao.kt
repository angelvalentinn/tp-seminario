package com.example.trabajopractico
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovie(movie: MovieEntity)  // Agregar una película a favoritos

    @Query("DELETE FROM movies_table WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)  // Eliminar una película de favoritos por ID

    @Query("SELECT * FROM movies_table")
    suspend fun getAllMovies(): List<MovieEntity>  // Obtener todas las películas favoritas

    @Query("SELECT * FROM movies_table WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): MovieEntity?  // Verificar si una película es favorita
}