package de.jensomato.sample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.jensomato.sample.R
import de.jensomato.sample.ui.model.CommentViewModel
import de.jensomato.sample.ui.model.PostViewModel

class CommentsAdapter :
    RecyclerView.Adapter<CommentsAdapter.MyViewHolder>() {

    var comments = listOf<CommentViewModel>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var username: TextView = view.findViewById(R.id.username)
        var comment: TextView = view.findViewById(R.id.comment)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_element, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment = comments[position]
        holder.username.text = comment.name
        holder.comment.text = comment.body
    }

    override fun getItemCount() = comments.size

}