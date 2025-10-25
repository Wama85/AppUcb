package com.calyrsoft.ucbp1.features.movie.presentation.viewmodel

import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import org.junit.Test
import org.junit.Assert.*

class MoviesSortingTest {

    @Test
    fun testMoviesSortedByRatingDescending() {
        val movies = listOf(
            Movie(1, "Movie 1", "", null, null, "", 0.0, 0, rating = 3f),
            Movie(2, "Movie 2", "", null, null, "", 0.0, 0, rating = 5f),
            Movie(3, "Movie 3", "", null, null, "", 0.0, 0, rating = 1f),
            Movie(4, "Movie 4", "", null, null, "", 0.0, 0, rating = 4f),
            Movie(5, "Movie 5", "", null, null, "", 0.0, 0, rating = 2f)
        )

        val sorted = movies.sortedByDescending { it.rating }

        assertEquals(5f, sorted[0].rating)
        assertEquals(4f, sorted[1].rating)
        assertEquals(3f, sorted[2].rating)
        assertEquals(2f, sorted[3].rating)
        assertEquals(1f, sorted[4].rating)

        assertEquals("Movie 2", sorted[0].title)
        assertEquals("Movie 4", sorted[1].title)
        assertEquals("Movie 1", sorted[2].title)
    }

    @Test
    fun testUnratedMoviesGoToBottom() {
        val movies = listOf(
            Movie(1, "Rated 5", "", null, null, "", 0.0, 0, rating = 5f),
            Movie(2, "Not rated", "", null, null, "", 0.0, 0, rating = 0f),
            Movie(3, "Rated 3", "", null, null, "", 0.0, 0, rating = 3f),
            Movie(4, "Not rated 2", "", null, null, "", 0.0, 0, rating = 0f)
        )

        val sorted = movies.sortedByDescending { it.rating }

        assertTrue(sorted[0].rating > 0)
        assertTrue(sorted[1].rating > 0)
        assertEquals(0f, sorted[2].rating)
        assertEquals(0f, sorted[3].rating)
    }
}