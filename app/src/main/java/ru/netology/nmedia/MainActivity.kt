package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostViewModel
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter ({
           viewModel.likeById(it.id)
        },
        {
            viewModel.shareById(it.id)
        })

        binding.list.adapter = adapter

        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)
        }



//
//        binding.share.setOnClickListener {
//            viewModel.share()
//        }
    }


    fun convertToString(i: Int):String {
        return when (i) {
            in 0..999 -> i.toString()
            in 1000..9999 -> (floor(i / 100.0) / 10.0).toString() + "K"
            in 10000..99999 -> (i/1000).toString() + "К"
            else -> (floor(i / 100000.0) / 10.0).toString() + "М"
        }
    }
}