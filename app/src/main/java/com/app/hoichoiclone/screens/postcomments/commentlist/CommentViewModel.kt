package com.app.hoichoiclone.screens.postcomments.commentlist

import androidx.lifecycle.*
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Comment
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Users
import com.app.hoichoiclone.screens.postcomments.commentlist.model.dbmodel.UserComments
import com.app.hoichoiclone.screens.postcomments.commentlist.repo.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CommentViewModel @Inject constructor(private val repository: CommentRepository) : ViewModel() {

    val allComment: LiveData<List<UserComments>> = repository.allComment.asLiveData()
    val allUsers: LiveData<List<Users>> = repository.allUsers.asLiveData()

    fun insert(comments: Comment) = viewModelScope.launch {
        repository.insert(comments)
    }

    fun insertUsers(users: List<Users>) = viewModelScope.launch {
        repository.insertUsers(users)
    }

   /* fun insert(item: Comment) =
        GlobalScope.launch {
            repository.insert(item)
        }*/
}
