package de.jensomato.sample.data

import de.jensomato.sample.appModule
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class JsonPlaceholderServiceTest: KoinTest {
    val apiService: JsonPlaceholderService by inject()

    @Before
    fun setup() {
        startKoin { modules(appModule) }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun getPosts() {
        val response = apiService.listPostsByUserId(1L).execute()

        assertTrue(response.isSuccessful)

        val posts = response.body().orEmpty()

        assertTrue(posts.isNotEmpty())
        assertTrue(posts.all { it.userId == 1L })
    }

    @Test
    fun getComments() {
        val response = apiService.listCommentsByPostsId(1L).execute()

        assertTrue(response.isSuccessful)

        val comments = response.body().orEmpty()

        assertTrue(comments.isNotEmpty())
        assertTrue(comments.all { it.postId == 1L })
    }
}