package com.calyrsoft.ucbp1.features.movie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.calyrsoft.ucbp1.features.movie.data.database.dao.IMovieDao
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 2)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieDao(): IMovieDao

    companion object {
        @Volatile
        private var Instance: MovieRoomDatabase? = null

        fun getDatabase(context: Context): MovieRoomDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieRoomDatabase::class.java, "movie_db")
                    .addMigrations(MOVIE_MIGRATION_1_2)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
