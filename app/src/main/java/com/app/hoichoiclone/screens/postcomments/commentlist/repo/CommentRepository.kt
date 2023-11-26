package com.app.hoichoiclone.screens.postcomments.commentlist.repo

import com.app.hoichoiclone.screens.postcomments.commentlist.dao.CommentDao
import com.app.hoichoiclone.screens.postcomments.commentlist.dao.UserDao
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Comment
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Users
import com.app.hoichoiclone.screens.postcomments.commentlist.model.dbmodel.UserComments
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class CommentRepository @Inject constructor(
    private val commentDao: CommentDao,
    private val userDao: UserDao
) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allComment: Flow<List<UserComments>> = commentDao.getUserComments()
    val allUsers: Flow<List<Users>> = userDao.getAllUsers()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.

    suspend fun insert(word: Comment) {
        commentDao.insert(word)
    }

    suspend fun insertUsers(users: List<Users>) {
        userDao.insert(users)
    }
}
