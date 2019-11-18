package de.jensomato.sample.respository

import com.github.kittinunf.result.Result
import de.jensomato.sample.data.JsonPlaceholderService
import de.jensomato.sample.data.model.PostModel

class PostsRepositroyImpl(val service: JsonPlaceholderService): PostsRepository {
    override fun listPostsByUserId(userId: Long): Result<List<PostModel>, Exception> {
        return try {
            service.listPostsByUserId(userId).execute().let {
                if (it.isSuccessful) {
                    Result.success(it.body().orEmpty())
                } else {
                    Result.error(RuntimeException("Error loading posts for userId=$userId"))
                }
            }
        } catch (ex: Exception) {
            Result.error(ex)
        }
    }
}