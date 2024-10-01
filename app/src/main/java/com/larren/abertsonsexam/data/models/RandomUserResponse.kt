package com.larren.abertsonsexam.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class RandomUserResponse(
    val results: List<User>? = null,
    val info: Info? = null
)

@Parcelize
data class User(
    val gender: String? = null,
    val name: Name? = null,
    val location: Location? = null,
    val picture: Picture? = null,
    val email: String? = null,
    val phone: String? = null
) : Parcelable {
    @Parcelize
    data class Name(
        val first: String? = null,
        val last: String? = null
    ) : Parcelable

    @Parcelize
    data class Location(
        val street: Street? = null,
        val city: String? = null,
        val state: String? = null,
        val country: String? = null,
        val postcode: String? = null
    ) : Parcelable {
        @Parcelize
        data class Street(
            val number: Int? = null,
            val name: String? = null
        ) : Parcelable
    }

    @Parcelize
    data class Picture(
        val large: String? = null,
        val medium: String? = null,
        val thumbnail: String? = null
    ) : Parcelable

    val fullName: String
        get() = "${name?.first} ${name?.last}"

    val address: String
        get() = "${location?.street?.name}, ${location?.street?.number}, ${location?.city}, ${location?.state}, ${location?.country}, ${location?.postcode}"
}

data class Info(
    val seed: String? = null,
    val results: Int? = null,
    val page: Int? = null,
    val version: String? = null
)
