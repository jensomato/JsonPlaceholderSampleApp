package de.jensomato.sample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.jensomato.sample.R
import de.jensomato.sample.ui.model.PostViewModel

class PostsAdapter :
    RecyclerView.Adapter<PostsAdapter.MyViewHolder>() {

    var posts = listOf<PostViewModel>()
    var onFavoriteClickListener: ((PostViewModel) -> Unit)? = null

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var body: TextView = view.findViewById(R.id.body)
        var button: ImageButton = view.findViewById(R.id.favoriteButton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_element, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = posts[position]
        holder.title.text = post.title
        holder.body.text = post.body
        holder.button.setImageResource(if (post.favorite) {
            R.drawable.ic_star_filled
        } else {
            R.drawable.ic_star_border
        })
        holder.button.setOnClickListener {
            onFavoriteClickListener?.invoke(post)
        }
    }

    override fun getItemCount() = posts.size

}