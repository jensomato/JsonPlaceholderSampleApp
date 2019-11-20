package de.jensomato.sample.presentation

import com.github.kittinunf.result.Result
import com.github.kittinunf.result.flatMap
import com.github.kittinunf.result.map
import de.jensomato.sample.respository.FavoritesRepository
import de.jensomato.sample.respository.PostsRepository
import de.jensomato.sample.ui.model.PostViewModel
import de.jensomato.sample.ui.model.PostsViewModel
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

    override fun loadPosts(userId: Long, showAll: Boolean) {
        coroutineScope.launch {
            val response = withContext(coroutineContextProvider.io) {
                postsRepository.listPostsByUserId(userId).flatMap {list ->
                    Result.of(list.map {post ->
                        favoritesRepository.isFavorite(post.id).map { isFavorite ->
                            PostViewModel(post.id, post.title, post.body, isFavorite, post.userId)
                        }
                    }.map { result ->
                        result.get()
                    }.filter { viewModel ->
                        showAll || viewModel.favorite
                    })
                }
            }

            response.fold(
                success = {
                    view.displayPosts(PostsViewModel(it, showAll.not()))
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