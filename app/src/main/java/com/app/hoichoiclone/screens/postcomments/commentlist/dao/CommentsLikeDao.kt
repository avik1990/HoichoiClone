package com.app.hoichoiclone.screens.postcomments.commentlist.dao

import androidx.room.*
import com.app.hoichoiclone.screens.postcomments.commentlist.model.CommentsLike
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentsLikeDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM comment_likes_tbl ORDER BY timeStamp ASC")
    fun getAlphabetizedWords(): Flow<List<CommentsLike>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: CommentsLike)

    @Delete
    suspend fun delete(item: CommentsLike)
    /* @Query("DELETE FROM comment_tbl")
     suspend fun deleteAll()*/
}
