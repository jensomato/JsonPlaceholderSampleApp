package de.jensomato.sample.presentation

import com.github.kittinunf.result.Result
import com.nhaarman.mockitokotlin2.*
import de.jensomato.sample.data.model.PostModel
import de.jensomato.sample.respository.FavoritesRepository
import de.jensomato.sample.respository.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class PostsPresenterTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private val postsRepository: PostsRepository = mock()
    private val favoritesRepository: FavoritesRepository = mock()
    private val view: PostsContract.View = mock()
    private val coroutineContextProvider = object : CoroutineContextProvider {
        override val main: CoroutineContext
            get() = Dispatchers.Unconfined
        override val io: CoroutineContext
            get() = Dispatchers.Unconfined
    }

    private val presenter = PostsPresenter(postsRepository, favoritesRepository, coroutineContextProvider, view, testScope)

    @Test
    fun testLoad() = testScope.runBlockingTest {
        val list = listOf(PostModel(1L, 1L, "title", "body"))
        whenever(postsRepository.listPostsByUserId(eq(1L))).thenReturn(
            Result.success(list)
        )
        whenever(favoritesRepository.isFavorite(1L)).thenReturn(Result.success(true))

        presenter.loadPosts(1L)

        verify(postsRepository).listPostsByUserId(eq(1L))
        verify(favoritesRepository).isFavorite(1L)
        verify(view).displayPosts(any())
    }
}