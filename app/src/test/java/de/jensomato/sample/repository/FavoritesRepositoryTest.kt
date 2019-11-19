package de.jensomato.sample.repository

import de.jensomato.sample.respository.FavoritesRepositroyImpl
import org.junit.Assert.*
import org.junit.Test

class FavoritesRepositoryTest {

    val repository = FavoritesRepositroyImpl()

    @Test
    fun testEmpty() {
        assertFalse(repository.isFavorite(1L).get())
        assertTrue(repository.listAllFavorites().get().isEmpty())
    }

    @Test
    fun testAddAndRemoveFavorites() {
        assertFalse(repository.isFavorite(1L).get())
        assertTrue(repository.listAllFavorites().get().isEmpty())

        repository.updateFavorite(1L, true)
        repository.updateFavorite(2L, true)
        repository.updateFavorite(3L, true)

        assertTrue(repository.isFavorite(1L).get())
        assertTrue(repository.isFavorite(2L).get())
        assertTrue(repository.isFavorite(3L).get())
        var favorites = repository.listAllFavorites().get()
        assertEquals(3, favorites.size)

        repository.updateFavorite(2L, false)
        assertTrue(repository.isFavorite(1L).get())
        assertFalse(repository.isFavorite(2L).get())
        assertTrue(repository.isFavorite(3L).get())
        favorites = repository.listAllFavorites().get()
        assertEquals(2, favorites.size)
    }

}

