package de.jensomato.sample.presentation

import de.jensomato.sample.ui.model.PostViewModel
import de.jensomato.sample.ui.model.PostsViewModel
import kotlinx.coroutines.CoroutineScope

object PostsContract {
    interface View: CoroutineScope {
        fun displayPosts(viewModel: PostsViewModel)
        fun displayError()
        fun navigateToComments(postId: Long)

        companion object {
            const val USER_ID = "userId"
        }
    }

    interface Presenter {
        fun loadPosts(userId: Long, showAll: Boolean = true)
        fun toggleFavoriteState(post: PostViewModel)
        fun selectPost(post: PostViewModel)
    }
}