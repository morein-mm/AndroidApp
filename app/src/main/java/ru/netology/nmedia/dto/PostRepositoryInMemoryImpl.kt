package ru.netology.nmedia.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl: PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась...",
        published = "21 мая в 18:56",
        likedByMe = false,
        likes = 10,
        shared = 25
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(likes = if (post.likedByMe) post.likes - 1 else post.likes + 1, likedByMe = !post.likedByMe)
        data.value = post
    }

    override fun share() {
        post = post.copy(shared = post.shared + 1)
        data.value = post
    }
}