package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val shared: Int,
    val video: String?
) {
    fun toDto() = Post(id, author, content, published, likes, likedByMe, shared, video)

    companion object {
        fun fromDto(post: Post) = PostEntity(
            id = post.id,
            author = post.author,
            content = post.content,
            published = post.published,
            likes = post.likes,
            likedByMe = post.likedByMe,
            shared = post.shared,
            video = post.video
        )
    }
}