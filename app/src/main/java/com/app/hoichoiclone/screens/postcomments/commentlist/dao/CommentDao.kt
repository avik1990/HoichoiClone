package com.app.hoichoiclone.screens.postcomments.commentlist.dao

import androidx.room.*
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Comment
import com.app.hoichoiclone.screens.postcomments.commentlist.model.dbmodel.UserComments
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("select c.timeStamp as commentTimestamp,c.comments,c.comment_Id, u.userName,u.usersPhoto,u.userId  from comment_tbl c  join users_tbl u on c.user_Id =u.userId order by c.timeStamp desc")
    fun getUserComments(): Flow<List<UserComments>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comment: Comment)

    @Delete
    suspend fun delete(item: Comment)
   /* @Query("DELETE FROM comment_tbl")
    suspend fun deleteAll()*/
}
