package ru.netology.nmedia.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl: PostRepository {
    private var posts = listOf(
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с р...",
            published = "18 сентября в 10:12",
            likedByMe = false,
            likes = 10,
            shared = 25
        ),
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась...",
            published = "21 мая в 18:56",
            likedByMe = false,
            likes = 10,
            shared = 25
        )
    )


    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map{
            if (it.id != id) it else it.copy(likes = if (it.likedByMe) it.likes - 1 else it.likes + 1, likedByMe = !it.likedByMe)
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map{
            if (it.id != id) it else it.copy(shared = it.shared + 1)
        }
        data.value = posts
    }

//    override fun share() {
//        post = post.copy(shared = post.shared + 1)
//        data.value = post
//    }
}