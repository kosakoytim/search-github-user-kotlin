package app.cermatitakehome.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import app.cermatitakehome.models.GithubUserSearchItemModel
import app.cermatitakehome.services.GithubServices
import app.cermatitakehome.utils.GITHUB_BASE_API
import app.cermatitakehome.views.activities.MainActivity
import app.cermatitakehome.views.adapters.GithubUserRecyclerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel() : ViewModel() {
    var searchData : MutableLiveData<ArrayList<GithubUserSearchItemModel>> = MutableLiveData()
    private val activityTag = "MAIN_ACTIVITY"

    private lateinit var disposable: Disposable
    val githubApiServe by lazy {
        GithubServices.create()
    }

    fun getGithubUsersData(searchQuery : String) {
        disposable = githubApiServe.getUsers(searchQuery)
            .subscribeOn(Schedulers.io())
            .map {
                if (it.items != null) {
                    var data = ArrayList<GithubUserSearchItemModel>()
                    it.items.forEach {
                        data.add(it)
                    }
                    searchData.postValue(data)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Log.d(activityTag, "ERROR")
                Log.d(activityTag, it.toString())
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}