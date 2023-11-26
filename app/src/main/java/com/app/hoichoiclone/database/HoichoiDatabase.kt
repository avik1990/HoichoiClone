package com.app.hoichoiclone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.hoichoiclone.screens.postcomments.commentlist.dao.CommentDao
import com.app.hoichoiclone.screens.postcomments.commentlist.dao.CommentsLikeDao
import com.app.hoichoiclone.screens.postcomments.commentlist.dao.UserDao
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Comment
import com.app.hoichoiclone.screens.postcomments.commentlist.model.CommentsLike
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Users
import com.app.hoichoiclone.screens.postcomments.reply.dao.ReplyDao
import com.app.hoichoiclone.screens.postcomments.reply.model.Reply
import com.app.hoichoiclone.utility.Constants.DATBASE_NAME
import javax.inject.Singleton

@Database(entities = [Comment::class, CommentsLike::class, Reply::class, Users::class], version = 3)
abstract class HoichoiDatabase : RoomDatabase() {

    @Singleton
    abstract fun getCommentDao(): CommentDao
    @Singleton
    abstract fun getReplyDao(): ReplyDao
    @Singleton
    abstract fun getUserDao(): UserDao
    @Singleton
    abstract fun getCommentLikeDao(): CommentsLikeDao

    companion object {
        @Volatile
        private var instance: HoichoiDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDatabase(
                        context
                    ).also { instance = it }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                HoichoiDatabase::class.java, DATBASE_NAME
            ).build()
    }
}
