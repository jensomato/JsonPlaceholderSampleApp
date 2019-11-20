package de.jensomato.sample.presentation

import com.github.kittinunf.result.map
import de.jensomato.sample.respository.PostsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter(
    val postsRepository: PostsRepository,
    val coroutineContextProvider: CoroutineContextProvider,
    val view: LoginContract.View,
    val coroutineScope: CoroutineScope = view
): LoginContract.Presenter {

    override fun login(userId: Long) {
        coroutineScope.launch {
            val response = withContext(coroutineContextProvider.io) {
                postsRepository.listPostsByUserId(userId).map { it.first().userId == userId }
            }
            response.fold(
                success = { userExists ->
                    if (userExists) {
                        view.navigateToPosts(userId)
                    } else {
                        view.displayError()
                    }
                },
                failure = { view.displayError() }
            )
        }
    }

}