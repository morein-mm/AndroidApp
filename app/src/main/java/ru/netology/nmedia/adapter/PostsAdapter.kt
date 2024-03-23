package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.OnInteractionListener
import ru.netology.nmedia.R
import ru.netology.nmedia.convertToString
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post



typealias onLikeListener = (Post) -> Unit
typealias onRemoveListener = (Post) -> Unit

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : androidx.recyclerview.widget.ListAdapter<Post, PostViewHolder>(PostDiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            postText.text = post.content
            like.isChecked = post.likedByMe
            like.text = convertToString(post.likes)
            share.text = convertToString(post.shared)
            if (post.video.isNullOrBlank()) {
                videoGroup.visibility = View.GONE
            } else {
                videoGroup.visibility = View.VISIBLE
            }
            videoPreview.setOnClickListener {
                onInteractionListener.playVideo(post)
            }
            playVideo.setOnClickListener {
                onInteractionListener.playVideo(post)
            }
            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            share.setOnClickListener {
                onInteractionListener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
            postText.setOnClickListener {
                onInteractionListener.onView(post)
            }
        }
    }
}

object PostDiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}