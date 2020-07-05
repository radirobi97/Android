package com.example.besthotels.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "location_search")
data class LocationSearch (

    @PrimaryKey
    @SerializedName("location_id")
    var locationId: Int,
    var name: String,
    var latitude: Double,
    var longitude: Double
)

data class LocationSearchResponse (
    var data : List<LocationSearchData>
)

data class LocationSearchData (
    @SerializedName("result_type")
    var resultType: String,

    @SerializedName("result_object")
    var resultObject: LocationSearch?
)
