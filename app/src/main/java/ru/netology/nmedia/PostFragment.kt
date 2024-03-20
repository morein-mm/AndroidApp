package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostViewModel
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.LongArg

class PostFragment : Fragment() {

    companion object {
        var Bundle.postIdArg: Long by LongArg
    }


    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(
            inflater,
            container,
            false
        )

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        callback.isEnabled = true

//        val binding = FragmentFeedBinding.inflate(inflater, container, false)
//
//        val viewModel: PostViewModel by viewModels()

//        val newPostLauncher = registerForActivityResult(NewPostContract) { result ->
//            if (result.isNullOrEmpty()) {
//                viewModel.cancelEdit()
//                return@registerForActivityResult
//            } else {FeedFragment
//                viewModel.changeContentAndSave(result)
//            }
//        }


        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)

                }
                val chooser = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(chooser)

                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
                findNavController().navigateUp()
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun playVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }

            override fun onView(post: Post) {

            }

        })

        binding.list.adapter = adapter

        val postId = arguments?.postIdArg ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe
            adapter.submitList(listOf(post))
        }




//        viewModel.data.observe(viewLifecycleOwner) { posts ->
//            val newPost = adapter.currentList.size < posts.size && adapter.currentList.size > 0
//            adapter.submitList(posts) {
////                if (newPost) {
////                    binding.list.smoothScrollToPosition(0)
////                }
//            }
//        }

//        viewModel.edited.observe(viewLifecycleOwner) { post ->
//            if (post.id != 0L) {
//                newPostLauncher.launch(post.content)
//            }
//        }

//        return binding.root
//
//
////        viewModel.edited.observe(this) { post ->
////            if (post.id != 0L) {
////                binding.edit.setText(post.content)
////                binding.edit.focusAndShowKeyboard()
////                binding.postLine.text = post.content
////                binding.group.visibility = View.VISIBLE
////            }
////        }
//

        return binding.root
    }


}