package de.jensomato.sample.respository

import com.github.kittinunf.result.Result
import de.jensomato.sample.data.model.PostModel

interface PostsRepository {
    fun listPostsByUserId(userId: Long): Result<List<PostModel>, Exception>
}