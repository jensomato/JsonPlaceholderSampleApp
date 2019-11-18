package de.jensomato.sample.repository

import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.jensomato.sample.data.JsonPlaceholderService
import de.jensomato.sample.data.model.PostModel
import de.jensomato.sample.respository.PostsRepositroyImpl
import org.junit.Assert.*
import org.junit.Test
import retrofit2.mock.Calls

class PostsRepositoryTest {

    val post1 = PostModel(1L, 1L, "title1", "body1")
    val post2 = PostModel(1L, 2L, "title2", "body2")
    val post3 = PostModel(1L, 3L, "title3", "body3")

    val service: JsonPlaceholderService = mock()
    val repository = PostsRepositroyImpl(service)

    @Test
    fun testSuccessWithList() {
        whenever(service.listPostsByUserId(eq(1L))).thenReturn(Calls.response(listOf(post1, post2, post3)))
        val result = repository.listPostsByUserId(1L)

        assertEquals(3, result.get().size)
        verify(service).listPostsByUserId(1L)
    }

    @Test
    fun testSuccessWithEmptyList() {
        whenever(service.listPostsByUserId(eq(1L))).thenReturn(Calls.response(emptyList()))
        val result = repository.listPostsByUserId(1L)

        assertTrue(result.get().isEmpty())
        verify(service).listPostsByUserId(1L)
    }

    @Test
    fun testSuccessWithUnexpectedException() {
        whenever(service.listPostsByUserId(eq(1L))).thenThrow(RuntimeException("unexpected error"))
        val result = repository.listPostsByUserId(1L)

        result.failure { assertNotNull(it) }
        result.success { fail("error expected") }
        verify(service).listPostsByUserId(1L)
    }

}

