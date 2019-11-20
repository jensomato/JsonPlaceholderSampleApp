package de.jensomato.sample.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.jensomato.sample.R
import de.jensomato.sample.presentation.CommentsContract.View.Companion.POST_ID
import de.jensomato.sample.presentation.PostsContract
import de.jensomato.sample.presentation.PostsContract.View.Companion.USER_ID
import de.jensomato.sample.ui.model.PostViewModel
import de.jensomato.sample.ui.model.PostsViewModel

import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.content_posts.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

class PostsActivity : AppCompatActivity(), PostsContract.View, KoinComponent, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private val postsPresenter: PostsContract.Presenter by inject { parametersOf(this) }
    private lateinit var viewAdapter: PostsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = SupervisorJob()
        setContentView(R.layout.activity_posts)
        setSupportActionBar(toolbar)

        viewManager = LinearLayoutManager(this)
        viewAdapter = PostsAdapter()
        viewAdapter.onFavoriteClickListener = ::updatePost
        viewAdapter.onPostClickListener = ::selectPost

        postsRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this@PostsActivity, LinearLayoutManager.VERTICAL))
        }

        val userId = intent.getLongExtra(USER_ID, 1L)

        buttonFilterFavs.setOnClickListener {
            postsPresenter.loadPosts(userId, false)
        }

        buttonFilterAll.setOnClickListener {
            postsPresenter.loadPosts(userId)
        }

    }

    private fun updatePost(post: PostViewModel) {
        postsPresenter.toggleFavoriteState(post)
    }

    private fun selectPost(post: PostViewModel) {
        postsPresenter.selectPost(post)
    }

    override fun onStart() {
        super.onStart()
        val userId = intent.getLongExtra(USER_ID, 1L)
        postsPresenter.loadPosts(userId)
    }

    override fun onStop() {
        super.onStop()
        job.cancelChildren()
    }

    override fun displayPosts(viewModel: PostsViewModel) {
        viewAdapter.posts = viewModel.posts
        viewAdapter.notifyDataSetChanged()
        buttonFilterAll.isEnabled = viewModel.isShowFavsOnly
        buttonFilterFavs.isEnabled = viewModel.isShowFavsOnly.not()
    }

    override fun displayError() {
        Toast.makeText(this, "display error", Toast.LENGTH_LONG).show()
    }

    override fun navigateToComments(postId: Long) {
        val intent = Intent(this, CommentsActivity::class.java).apply {
            putExtra(POST_ID, postId)
        }
        startActivity(intent)
    }
}
