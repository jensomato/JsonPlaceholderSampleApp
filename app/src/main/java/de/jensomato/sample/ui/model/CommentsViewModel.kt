package de.jensomato.sample.ui.model

data class CommentsViewModel(
    val post: PostViewModel,
    val comments: List<CommentViewModel>
)