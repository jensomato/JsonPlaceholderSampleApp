package de.jensomato.sample.respository

import com.github.kittinunf.result.Result
import de.jensomato.sample.data.JsonPlaceholderService
import de.jensomato.sample.data.model.CommentModel

class CommentsRepositroyImpl(val service: JsonPlaceholderService): CommentsRepository {
    override fun listCommentsByPostId(postId: Long): Result<List<CommentModel>, Exception> {
        return try {
            service.listCommentsByPostsId(postId).execute().let {
                if (it.isSuccessful) {
                    Result.success(it.body().orEmpty())
                } else {
                    Result.error(RuntimeException("Error loading comments for postId=$postId"))
                }
            }
        } catch (ex: Exception) {
            Result.error(ex)
        }
    }
}