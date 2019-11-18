package de.jensomato.sample.respository

import com.github.kittinunf.result.Result
import de.jensomato.sample.data.model.CommentModel

interface CommentsRepository {
    fun listCommentsByPostId(postId: Long): Result<List<CommentModel>, Exception>
}