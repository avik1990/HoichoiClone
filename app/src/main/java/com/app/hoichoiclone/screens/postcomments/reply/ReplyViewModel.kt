package com.app.hoichoiclone.screens.postcomments.reply

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Comment
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Users
import com.app.hoichoiclone.screens.postcomments.commentlist.model.dbmodel.UserComments
import com.app.hoichoiclone.screens.postcomments.commentlist.repo.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ReplyViewModel @Inject constructor(private val repository: CommentRepository) : ViewModel() {

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
