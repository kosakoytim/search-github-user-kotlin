package app.cermatitakehome.services

import app.cermatitakehome.models.GithubUserSearchModel
import app.cermatitakehome.utils.GITHUB_BASE_API
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubServices {

    @GET("/search/users")
    fun getUsers(@Query("q") searchQuery: String) : Observable<GithubUserSearchModel>

    companion object {
        fun create() : GithubServices {
            val retrofit : Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GITHUB_BASE_API)
                .build()

            return retrofit.create(GithubServices::class.java)
        }
    }
}