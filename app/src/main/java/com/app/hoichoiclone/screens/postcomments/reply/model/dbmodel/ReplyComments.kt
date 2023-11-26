package com.app.hoichoiclone.screens.postcomments.reply.model.dbmodel

data class ReplyComments(
    val commentTimestamp: Long,
    val comments: String,
    val comment_Id: String,
    val userName: String,
    val usersPhoto: String,
    val userId: String
)
