package de.jensomato.sample.respository

import com.github.kittinunf.result.Result
import de.jensomato.sample.data.model.FavoriteModel

interface FavoritesRepository {
    fun isFavorite(postId: Long): Result<Boolean, Exception>
    fun listAllFavorites(): Result<List<FavoriteModel>, Exception>
    fun updateFavorite(postId: Long, isFavorite: Boolean): Result<Unit, Exception>
}