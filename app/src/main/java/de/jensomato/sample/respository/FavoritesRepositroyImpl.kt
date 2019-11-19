package de.jensomato.sample.respository

import com.github.kittinunf.result.Result
import de.jensomato.sample.data.model.FavoriteModel

class FavoritesRepositroyImpl: FavoritesRepository {
    private val favoritesMap = mutableMapOf<Long, Boolean>()

    override fun isFavorite(postId: Long): Result<Boolean, Exception> {
        return Result.success(favoritesMap[postId] ?: false)
    }

    override fun listAllFavorites(): Result<List<FavoriteModel>, Exception> {
        return Result.success(favoritesMap.map { FavoriteModel(it.key, it.value) })
    }

    override fun updateFavorite(postId: Long, isFavorite: Boolean): Result<Unit, Exception> {
        if (isFavorite) {
            favoritesMap[postId] = isFavorite
        } else {
            favoritesMap.remove(postId)
        }
        return Result.success(Unit)
    }
}