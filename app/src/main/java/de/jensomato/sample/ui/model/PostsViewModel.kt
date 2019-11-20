package de.jensomato.sample.ui.model

data class PostsViewModel(
    val posts: List<PostViewModel>,
    val isShowFavsOnly: Boolean
)