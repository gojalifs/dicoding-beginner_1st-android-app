package com.satria.dicoding.latihan.latihanretrofit_restaurantreview.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RestaurantResponse(

    @field:SerializedName("restaurant")
    val restaurant: Restaurant? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable

@Parcelize
data class Restaurant(

    @field:SerializedName("customerReviews")
    val customerReviews: List<CustomerReviewsItem?>? = null,

    @field:SerializedName("pictureId")
    val pictureId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("rating")
    val rating: Double? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null
) : Parcelable

@Parcelize
data class CustomerReviewsItem(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("review")
    val review: String? = null,

    @field:SerializedName("name")
    val name: String? = null
) : Parcelable

data class PostReviewResponse(
    @field:SerializedName("customerReviews")
    val customerReviews: List<CustomerReviewsItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)