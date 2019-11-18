package de.jensomato.sample.data

import de.jensomato.sample.data.model.CommentModel
import de.jensomato.sample.data.model.PostModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface JsonPlaceholderService {

    @GET("posts")
    fun listPostsByUserId(@Query("userId") userId: Long): Call<List<PostModel>>

    @GET("comments")
    fun listCommentsByPostsId(@Query("postId") userId: Long): Call<List<CommentModel>>
}