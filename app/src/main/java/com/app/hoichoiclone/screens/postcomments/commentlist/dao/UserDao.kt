package com.app.hoichoiclone.screens.postcomments.commentlist.dao

import androidx.room.*
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM users_tbl ORDER BY timeStamp ASC")
    fun getAllUsers(): Flow<List<Users>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: List<Users>)

    @Delete
    suspend fun delete(item: Users)
    /* @Query("DELETE FROM comment_tbl")
     suspend fun deleteAll()*/
}
