package com.unicorndevelopers.inkspiration.models

    data class ImageResponseModel(
    val success: Boolean,
    val message: String,
    val photos: List<Photo>
) {
    data class Photo(
        val file_size: Int,
        val title: String,
        val description: String,
        val height: Int,
        val updated_at: String,
        val id: Int,
        val width: Int,
        val created_at: String,
        val url: String
    )
}
