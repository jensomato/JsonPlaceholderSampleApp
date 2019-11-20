package de.jensomato.sample.presentation

import kotlinx.coroutines.CoroutineScope

object LoginContract {
    interface View: CoroutineScope {
        fun displayError()
        fun navigateToPosts(userId: Long)
    }

    interface Presenter {
        fun login(userId: Long)
    }
}