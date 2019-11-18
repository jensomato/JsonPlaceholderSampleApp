package de.jensomato.sample.repository

import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.jensomato.sample.data.JsonPlaceholderService
import de.jensomato.sample.data.model.CommentModel
import de.jensomato.sample.respository.CommentsRepositroyImpl
import org.junit.Assert.*
import org.junit.Test
import retrofit2.mock.Calls

class CommentsRepositoryTest {

    val comment1 = CommentModel(1L, 1L, "name1", "email1", "body1")
    val comment2 = CommentModel(1L, 2L, "name2", "email2", "body2")
    val comment3 = CommentModel(1L, 3L, "name3", "email3", "body3")

    val service: JsonPlaceholderService = mock()
    val repository = CommentsRepositroyImpl(service)

    @Test
    fun testSuccessWithList() {
        whenever(service.listCommentsByPostsId(eq(1L))).thenReturn(Calls.response(listOf(comment1, comment2, comment3)))
        val result = repository.listCommentsByPostId(1L)

        assertEquals(3, result.get().size)
        verify(service).listCommentsByPostsId(1L)
    }

    @Test
    fun testSuccessWithEmptyList() {
        whenever(service.listCommentsByPostsId(eq(1L))).thenReturn(Calls.response(emptyList()))
        val result = repository.listCommentsByPostId(1L)

        assertTrue(result.get().isEmpty())
        verify(service).listCommentsByPostsId(1L)
    }

    @Test
    fun testSuccessWithUnexpectedException() {
        whenever(service.listCommentsByPostsId(eq(1L))).thenThrow(RuntimeException("unexpected error"))
        val result = repository.listCommentsByPostId(1L)

        result.failure { assertNotNull(it) }
        result.success { fail("error expected") }
        verify(service).listCommentsByPostsId(1L)
    }

}

