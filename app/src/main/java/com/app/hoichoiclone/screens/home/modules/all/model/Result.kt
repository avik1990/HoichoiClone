package com.app.hoichoiclone.screens.home.modules.all.model

data class Result(
    // @Expose(deserialize = false) val viewType: Int,
    val details: List<Detail>,
    val id: Int,
    val title: String
)
