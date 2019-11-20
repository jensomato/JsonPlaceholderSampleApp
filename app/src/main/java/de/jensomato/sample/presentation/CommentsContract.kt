package de.jensomato.sample.presentation

import de.jensomato.sample.ui.model.CommentsViewModel
import kotlinx.coroutines.CoroutineScope

object CommentsContract {
    interface View: CoroutineScope {
        fun displayComments(comments: CommentsViewModel)
        fun displayError()

        companion object {
            const val POST_ID = "postId"
        }
    }

    interface Presenter {
        fun loadComments(postId: Long)
    }
}