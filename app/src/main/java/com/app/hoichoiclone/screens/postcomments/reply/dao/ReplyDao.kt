package com.app.hoichoiclone.screens.postcomments.reply.dao

import androidx.room.*
import com.app.hoichoiclone.screens.postcomments.reply.model.Reply
import kotlinx.coroutines.flow.Flow

@Dao
interface ReplyDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM reply_tbl ORDER BY timeStamp ASC")
    fun getAlphabetizedWords(): Flow<List<Reply>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Reply)

    @Delete
    suspend fun delete(item: Reply)
    /* @Query("DELETE FROM comment_tbl")
     suspend fun deleteAll()*/
}
