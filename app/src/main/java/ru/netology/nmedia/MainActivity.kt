package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostContract) { result ->
            if (result.isNullOrEmpty()) {
                viewModel.cancelEdit()
                return@registerForActivityResult
            } else {
                viewModel.changeContentAndSave(result)
            }
        }

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


            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun playVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }

        })

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            val newPost = adapter.currentList.size < posts.size && adapter.currentList.size > 0
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch(null)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                newPostLauncher.launch(post.content)
            }
        }


//        viewModel.edited.observe(this) { post ->
//            if (post.id != 0L) {
//                binding.edit.setText(post.content)
//                binding.edit.focusAndShowKeyboard()
//                binding.postLine.text = post.content
//                binding.group.visibility = View.VISIBLE
//            }
//        }

//        binding.save.setOnClickListener {
//            newPostLauncher.launch()
////            val text = binding.edit.text.toString().trim()
////            if (text.isEmpty()) {
////                Toast.makeText(this, R.string.error_empty_content, Toast.LENGTH_LONG).show()
////                return@setOnClickListener
////            }
////            viewModel.changeContentAndSave(text)
////            binding.postLine.text = ""
////            binding.group.visibility = View.GONE
////            binding.edit.setText("")
////            binding.edit.clearFocus()
////            AndroidUtils.hideKeyboard(it)
//        }
//
//        binding.cancelIcon.setOnClickListener {
//            binding.postLine.text = ""
//            binding.group.visibility = View.GONE
//            binding.edit.setText("")
//            binding.edit.clearFocus()
//            AndroidUtils.hideKeyboard(it)
//            viewModel.cancelEdit()
//        }


    }


}