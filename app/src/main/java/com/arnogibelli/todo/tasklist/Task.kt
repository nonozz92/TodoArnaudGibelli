package com.arnogibelli.todo.tasklist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Task(
    @SerialName ("id")
    val id: String,
    @SerialName ("title")
    val title: String="voici le title",
    @SerialName ("description")
    val description: String ="voici la decription"):java.io.Serializable

