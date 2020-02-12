package app.cermatitakehome.models

import com.google.gson.annotations.SerializedName

data class GithubUserSearchItemModel (
    @SerializedName("login")
    var username: String? = null,
    @SerializedName("avatar_url")
    var avatar: String? = null
)

data class GithubUserSearchModel (
    val total_count: Int? = null,
    val incomplete_results: Boolean? = null,
    val items: List<GithubUserSearchItemModel>? = null
)