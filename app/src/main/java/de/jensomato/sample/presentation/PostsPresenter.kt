package de.jensomato.sample.presentation

import de.jensomato.sample.respository.FavoritesRepository
import de.jensomato.sample.respository.PostsRepository
import de.jensomato.sample.ui.model.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsPresenter(
    val postsRepository: PostsRepository,
    val favoritesRepository: FavoritesRepository,
    val coroutineContextProvider: CoroutineContextProvider,
    val view: PostsContract.View,
    val coroutineScope: CoroutineScope = view
): PostsContract.Presenter {

    override fun loadPosts(userId: Long) {
        coroutineScope.launch {
            val response = withContext(coroutineContextProvider.io) {
                postsRepository.listPostsByUserId(userId)
            }

            response.fold(
                success = {
                    view.displayPosts(it.map { post ->
                        PostViewModel(post.id, post.title, post.body, favoritesRepository.isFavorite(post.id).get(), post.userId)
                    })
                },
                failure = {
                    view.displayError()
                }
            )
        }
    }

    override fun toggleFavoriteState(post: PostViewModel) {
        coroutineScope.launch {
            withContext(coroutineContextProvider.io) {
                favoritesRepository.updateFavorite(post.id, post.favorite.not())
            }.fold(
                success = {
                    loadPosts(post.userId)
                },
                failure = {
                    view.displayError()
                })
        }
    }

    override fun selectPost(post: PostViewModel) {
        view.navigateToComments(post.id)
    }
}