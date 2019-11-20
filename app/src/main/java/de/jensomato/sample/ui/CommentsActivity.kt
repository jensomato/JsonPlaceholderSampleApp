package de.jensomato.sample.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.jensomato.sample.R
import de.jensomato.sample.presentation.CommentsContract
import de.jensomato.sample.presentation.CommentsContract.View.Companion.POST_ID
import de.jensomato.sample.ui.model.CommentsViewModel
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.activity_posts.toolbar
import kotlinx.android.synthetic.main.post_element.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

class CommentsActivity : AppCompatActivity(), CommentsContract.View, KoinComponent, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private val commentsPresenter: CommentsContract.Presenter by inject { parametersOf(this) }
    private lateinit var viewAdapter: CommentsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = SupervisorJob()
        setContentView(R.layout.activity_comments)
        setSupportActionBar(toolbar)

        viewManager = LinearLayoutManager(this)
        viewAdapter = CommentsAdapter()

        commentsRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this@CommentsActivity, LinearLayoutManager.VERTICAL))
        }

    }

    override fun onStart() {
        super.onStart()
        val postId = intent.getLongExtra(POST_ID, -1L)
        commentsPresenter.loadComments(postId)
    }

    override fun onStop() {
        super.onStop()
        job.cancelChildren()
    }

    override fun displayComments(comments: CommentsViewModel) {
        viewAdapter.comments = comments.comments
        viewAdapter.notifyDataSetChanged()
        postBody.text = comments.post.body
        postTitle.text = comments.post.title
        favoriteButton.setImageResource(if (comments.post.favorite) {
            R.drawable.ic_star_filled
        } else {
            R.drawable.ic_star_border
        })
    }

    override fun displayError() {
        Toast.makeText(this, "display error", Toast.LENGTH_LONG).show()
    }
}
