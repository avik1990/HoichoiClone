package com.app.hoichoiclone.di

import android.content.Context
import androidx.room.Room
import com.app.hoichoiclone.database.HoichoiDatabase
import com.app.hoichoiclone.screens.postcomments.commentlist.model.Comment
import com.app.hoichoiclone.utility.Constants.DATBASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, HoichoiDatabase::class.java, DATBASE_NAME
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: HoichoiDatabase) = db.getCommentDao()

    @Provides
    @Singleton
    fun provideReplyDao(db: HoichoiDatabase) = db.getReplyDao()

    @Provides
    @Singleton
    fun provideCommentLikeDao(db: HoichoiDatabase) = db.getCommentLikeDao()

    @Provides
    @Singleton
    fun provideUserDao(db: HoichoiDatabase) = db.getUserDao()

    @Provides
    fun provideEntity() = Comment()
}
