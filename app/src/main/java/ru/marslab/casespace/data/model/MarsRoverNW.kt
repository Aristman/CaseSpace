package ru.marslab.casespace.data.model


import com.google.gson.annotations.SerializedName

data class MarsRoverNW(
    @SerializedName("photo_manifest")
    val photoManifest: PhotoManifest
) {
    data class PhotoManifest(
        @SerializedName("landing_date")
        val landingDate: String,
        @SerializedName("launch_date")
        val launchDate: String,
        @SerializedName("max_date")
        val maxDate: String,
        @SerializedName("max_sol")
        val maxSol: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("photos")
        val photos: List<Photo>,
        @SerializedName("status")
        val status: String,
        @SerializedName("total_photos")
        val totalPhotos: Int
    ) {
        data class Photo(
            @SerializedName("cameras")
            val cameras: List<String>,
            @SerializedName("earth_date")
            val earthDate: String,
            @SerializedName("sol")
            val sol: Int,
            @SerializedName("total_photos")
            val totalPhotos: Int
        )
    }
}