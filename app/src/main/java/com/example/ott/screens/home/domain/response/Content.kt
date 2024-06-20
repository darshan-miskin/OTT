package com.example.ott.screens.home.domain.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Content(
    @SerializedName("name") var name: String? = null,
    @SerializedName("poster-image") var posterImage: String? = null
): Serializable {

    @ColumnInfo(name = "page_num")
    var pageNumber = 0

    /**
     * Making hashcode primary code so eliminate entry of duplicate items.
     * Without this, database & recycler adapter have inconsistent data, causing unexpected crashes.
     */
    @PrimaryKey
    @ColumnInfo(name = "hash")
    var key = hashCode()
}