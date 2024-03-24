package ru.netology.nmedia.dto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.repository.PostRepositoryRoomImpl

val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    likes = 0,
    shared = 0,
    video = null
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    //    private val repository: PostRepository = PostRepositoryFileImpl(application)
    private val repository: PostRepository = PostRepositoryRoomImpl(
        AppDb.getInstance(application).postDao
    )


    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun changeContentAndSave(content: String) {
        edited.value?.let {
            if (content != it.content) {
                repository.save(it.copy(content = content))
            }
            edited.value = empty
        }
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun edit(post: Post) {
        edited.value = post
    }
    fun cancelEdit() {
        edited.value = empty
    }

}