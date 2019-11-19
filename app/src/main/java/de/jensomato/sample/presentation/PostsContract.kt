package de.jensomato.sample.presentation

import de.jensomato.sample.ui.model.PostViewModel
import kotlinx.coroutines.CoroutineScope

object PostsContract {
    interface View: CoroutineScope {
        fun displayPosts(posts: List<PostViewModel>)
        fun displayError()

        companion object {
            const val USER_ID = "userId"
        }
    }

    interface Presenter {
        fun loadPosts(userId: Long)
        fun toggleFavoriteState(post: PostViewModel)
    }
}