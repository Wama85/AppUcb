package com.calyrsoft.ucbp1.features.movie.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity

@Dao
interface IMovieDao {
    @Query("SELECT * FROM movies ORDER BY user_rating DESC")
    suspend fun getAllOrderedByRating(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getById(movieId: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Update
    suspend fun update(movie: MovieEntity)

    @Query("UPDATE movies SET user_rating = :rating WHERE id = :movieId")
    suspend fun updateRating(movieId: Int, rating: Float)

    @Query("UPDATE movies SET watch_later = :watchLater WHERE id = :movieId")
    suspend fun updateWatchLater(movieId: Int, watchLater: Boolean)

    @Query("SELECT * FROM movies WHERE watch_later = 1 ORDER BY timestamp DESC")
    suspend fun getWatchLaterMovies(): List<MovieEntity>

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteById(movieId: Int)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("SELECT * FROM movies WHERE user_rating > 0 ORDER BY user_rating DESC")
    suspend fun getRatedMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE user_rating >= :minRating ORDER BY user_rating DESC")
    suspend fun getMoviesWithMinRating(minRating: Float): List<MovieEntity>
}
