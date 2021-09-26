package ru.marslab.casespace.data.model


import com.google.gson.annotations.SerializedName

data class EpicNW(
    @SerializedName("attitude_quaternions")
    val attitudeQuaternions: AttitudeQuaternions,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("centroid_coordinates")
    val centroidCoordinates: Coordinates,
    @SerializedName("coords")
    val coords: Coords,
    @SerializedName("date")
    val date: String,
    @SerializedName("dscovr_j2000_position")
    val dscovrJ2000Position: SpacePosition,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("lunar_j2000_position")
    val lunarJ2000Position: SpacePosition,
    @SerializedName("sun_j2000_position")
    val sunJ2000Position: SpacePosition,
    @SerializedName("version")
    val version: String
) {
    data class AttitudeQuaternions(
        @SerializedName("q0")
        val q0: Double,
        @SerializedName("q1")
        val q1: Double,
        @SerializedName("q2")
        val q2: Double,
        @SerializedName("q3")
        val q3: Double
    )

    data class Coords(
        @SerializedName("attitude_quaternions")
        val attitudeQuaternions: AttitudeQuaternions,
        @SerializedName("centroid_coordinates")
        val centroidCoordinates: Coordinates,
        @SerializedName("dscovr_j2000_position")
        val dscovrJ2000Position: SpacePosition,
        @SerializedName("lunar_j2000_position")
        val lunarJ2000Position: SpacePosition,
        @SerializedName("sun_j2000_position")
        val sunJ2000Position: SpacePosition
    ) {
        data class AttitudeQuaternions(
            @SerializedName("q0")
            val q0: Double,
            @SerializedName("q1")
            val q1: Double,
            @SerializedName("q2")
            val q2: Double,
            @SerializedName("q3")
            val q3: Double
        )
    }

    data class Coordinates(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double
    )

    data class SpacePosition(
        @SerializedName("x")
        val x: Double,
        @SerializedName("y")
        val y: Double,
        @SerializedName("z")
        val z: Double
    )
}