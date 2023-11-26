package com.app.hoichoiclone.screens.postcomments.commentlist.model.dbmodel

data class UserComments(
    val commentTimestamp: Long,
    val comments: String,
    val comment_Id: String,
    val userName: String,
    val usersPhoto: String,
    val userId: String
)
