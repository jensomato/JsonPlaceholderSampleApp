package de.jensomato.sample.ui.model

data class PostViewModel(
    val id: Long,
    val title: String,
    val body: String,
    val favorite: Boolean,
    val userId: Long
)