package com.app.hoichoiclone.screens.comments.commentlist.dao

import androidx.room.*
import com.app.hoichoiclone.screens.comments.commentlist.model.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM comment_tbl ORDER BY timeStamp ASC")
    fun getAlphabetizedWords(): Flow<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Comment)

    @Delete
    suspend fun delete(item: Comment)
   /* @Query("DELETE FROM comment_tbl")
    suspend fun deleteAll()*/
}
