package de.jensomato.sample.presentation

import com.github.kittinunf.result.flatMap
import com.github.kittinunf.result.map
import de.jensomato.sample.respository.CommentsRepository
import de.jensomato.sample.respository.FavoritesRepository
import de.jensomato.sample.respository.PostsRepository
import de.jensomato.sample.ui.model.CommentViewModel
import de.jensomato.sample.ui.model.CommentsViewModel
import de.jensomato.sample.ui.model.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentsPresenter(
    val postsRepository: PostsRepository,
    val favoritesRepository: FavoritesRepository,
    val commentsRepository: CommentsRepository,
    val coroutineContextProvider: CoroutineContextProvider,
    val view: CommentsContract.View,
    val coroutineScope: CoroutineScope = view
): CommentsContract.Presenter {

    override fun loadComments(postId: Long) {
        coroutineScope.launch {

            val response = withContext(coroutineContextProvider.io) {
                postsRepository.getPost(postId).flatMap { post ->
                    favoritesRepository.isFavorite(post.id).map { isFavorite ->
                        PostViewModel(post.id, post.title, post.body, isFavorite, post.userId)
                    }.flatMap { postViewModel ->
                        commentsRepository.listCommentsByPostId(postViewModel.id).map { comments ->
                            CommentsViewModel(postViewModel, comments.map { CommentViewModel(it.id, it.name, it.body, it.email) })
                        }
                    }
                }
            }

            response.fold(
                success = {
                    view.displayComments(it)
                },
                failure = {
                    view.displayError()
                }
            )
        }
    }

}