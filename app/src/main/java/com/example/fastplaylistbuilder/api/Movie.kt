package com.example.fastplaylistbuilder.api

import com.example.fastplaylistbuilder.adapters.ListAdapterItem
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Year")
    val year: String?,
    @SerializedName("Rated")
    val rating: String?,
    @SerializedName("Poster")
    val poster: String?,
    @SerializedName("Title")
    override var title: String? = null
) : ListAdapterItem