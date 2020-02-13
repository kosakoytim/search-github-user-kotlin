package app.cermatitakehome.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.cermatitakehome.models.GithubUserSearchItemModel
import app.cermatitakehome.services.GithubServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivityViewModel() : ViewModel() {
    var searchData : ArrayList<GithubUserSearchItemModel> = ArrayList()
    var searchStatus : MutableLiveData<SearchStatus> = MutableLiveData()
    var searchString : String = String()

    init {
        searchStatus.postValue(SearchStatus.AWAITING_INPUT)
    }

    private val activityTag = "MAIN_ACTIVITY"

    private lateinit var disposable: Disposable
    val githubApiServe by lazy {
        GithubServices.create()
    }

    private fun dataNotEmpty(items : List<GithubUserSearchItemModel>) : Boolean {
        if (items.isEmpty()) {
            searchStatus.postValue(SearchStatus.NOT_FOUND)
            return false
        }
        return true
    }

    private fun inputNotEmpty(input : String) : Boolean {
        if (input.length == 0) {
            searchStatus.postValue(SearchStatus.NO_INPUT)
            return false
        }
        return true
    }

    private fun inputShouldRunOnce(input: String) : Boolean {
        if (input == searchString) {
            return false
        }
        return true
    }

    fun getGithubUsersData(searchQuery : String) {
        if (inputNotEmpty(searchQuery) && inputShouldRunOnce(searchQuery)) {
            searchString = searchQuery
            searchStatus.postValue(SearchStatus.LOADING)
            disposable = githubApiServe.getUsers(searchQuery)
                .subscribeOn(Schedulers.io())
                .map {
                    if (it.items != null && dataNotEmpty(it.items)) {
                        var data = ArrayList<GithubUserSearchItemModel>()
                        it.items.forEach {
                            data.add(it)
                        }
                        searchData = data
                        searchStatus.postValue(SearchStatus.COMPLETE)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {},
                    {
                        if (it.message != null) {
                            if (it.message.toString().contains("403")) { searchStatus.postValue(SearchStatus.ERROR_403) }
                            else if (it.message.toString().contains("422")) { searchStatus.postValue(SearchStatus.ERROR_422) }
                            else { searchStatus.postValue(SearchStatus.ERROR_UNKNOWN) }
                        }
                        Log.w(activityTag, it.toString())
                    }
                )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}