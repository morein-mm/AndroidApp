package ru.netology.nmedia

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostViewModel

interface OnInteractionListener {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onView(post: Post)
    fun playVideo(post: Post)
}

open class InteractionListener(private val context: Context, private val viewModel: PostViewModel, private val fragment: Fragment) : OnInteractionListener {

    override fun onLike(post: Post) {
        viewModel.likeById(post.id)
    }
    override fun onShare(post: Post) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, post.content)

        }
        val chooser = Intent.createChooser(intent, context.getString(R.string.chooser_share_post))
        context.startActivity(chooser)
        viewModel.shareById(post.id)
    }
    override fun onRemove(post: Post) {
        viewModel.removeById(post.id)
    }
    override fun onEdit(post: Post) {
        findNavController(fragment).navigate(
            R.id.action_feedFragment_to_newPostFragment,
            Bundle().apply {
                textArg = post.content
            }
        )
        viewModel.edit(post)
    }
    override fun onView(post: Post) {
//        findNavController(fragment).navigate(
//            R.id.action_feedFragment_to_postFragment,
//            Bundle().apply {
//                postIdArg = post.id
//            }
//        )
    }
    override fun playVideo(post: Post) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
        context.startActivity(intent)
    }
}