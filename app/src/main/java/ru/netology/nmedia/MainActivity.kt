package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению.",
            likes = 8456,
            shared = 1567856
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            postText.text = post.content
            liked.text = convertToString(post.likes)
            shared.text = convertToString(post.shared)


            if(post.likedByMe) {
                like?.setImageResource(R.drawable.baseline_thumb_up_red_24)
            }

            like?.setOnClickListener {
                if(post.likedByMe) post.likes-- else post.likes++
                liked.text = convertToString(post.likes)
                post.likedByMe = !post.likedByMe
                like?.setImageResource(
                    if (post.likedByMe) R.drawable.baseline_thumb_up_red_24 else R.drawable.baseline_thumb_up_24
                )
            }

            share?.setOnClickListener {
                post.shared++
                shared.text = convertToString(post.shared)
            }
        }
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