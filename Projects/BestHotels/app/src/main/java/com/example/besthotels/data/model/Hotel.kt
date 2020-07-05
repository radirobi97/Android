package com.example.besthotels.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class HotelResponse(
    var data : List<Hotel>
)


@Entity(tableName = "hotels")
data class Hotel(
    @PrimaryKey
    @SerializedName("location_id")
    var locationId: Int,

    var locationSearchId: Int,

    var checkIn: String,

    var adults: Int,

    var rooms: Int,

    var nights: Int,

    var maxPrice: Int,

    var hotelClass: Float,

    var amenityInput: String,

    var name: String,

    var latitude: Double,

    var longitude: Double,

    @SerializedName("num_reviews")
    var numReviews: Int?,

    var ranking: String?,

    var rating: Float?,

    @SerializedName("price_level")
    var priceLevel: String?,

    var price: String?,

    var photo: Photo?
)

data class HotelDetailsResponse(
    var data : List<HotelDetails>
)

@Entity(tableName = "hotel_details")
data class HotelDetails(

    @PrimaryKey
    @SerializedName("location_id")
    var locationId: Int,

    var name: String,

    var latitude: Double,

    var longitude: Double,

    @SerializedName("num_reviews")
    var numReviews: Int?,

    var ranking: String?,

    var rating: Float?,

    @SerializedName("hotel_class")
    var hotelClass: String,

    var phone: String?,

    var website: String?,

    var email: String?,

    var address: String?,

    var description: String?,

    @SerializedName("price_level")
    var priceLevel: String?,

    var price: String?,

    var photo: Photo?,

    var amenities: List<Amenity>
)


@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey
    var images: Images
)


@Entity(tableName = "images")
data class Images(
    @PrimaryKey
    var original: Original
)


@Entity(tableName = "originals")
data class Original(
    @PrimaryKey
    var url: String,

    var width: String,

    var height: String
)


@Entity(tableName = "amenities")
data class Amenity(
    @PrimaryKey
    var key: String,

    var name: String
)