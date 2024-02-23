package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.convertToString
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post

typealias onLikeListener = (Post) -> Unit
class PostsAdapter(private val onLike: onLikeListener, private val onShare: onLikeListener) : androidx.recyclerview.widget.ListAdapter<Post, PostViewHolder>(PostDiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onLike, onShare)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(private val binding: PostCardBinding, private val onLike: onLikeListener, private val onShare: onLikeListener) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            postText.text = post.content
            liked.text = convertToString(post.likes)
            shared.text = convertToString(post.shared)
//            liked.text = post.likes.toString()
//            shared.text = post.shared.toString()
            like.setImageResource(
                if(post.likedByMe) R.drawable.baseline_thumb_up_red_24 else R.drawable.baseline_thumb_up_24
            )
            like.setOnClickListener{
                onLike(post)
            }
            share.setOnClickListener{
                onShare(post)
            }
        }


    }
}

object PostDiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}