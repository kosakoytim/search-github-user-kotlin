package app.cermatitakehome.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import app.cermatitakehome.views.adapters.GithubUserRecyclerAdapter
import app.cermatitakehome.R
import app.cermatitakehome.viewModels.MainActivityViewModel
import app.cermatitakehome.viewModels.SearchStatus
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_bar.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this)[MainActivityViewModel::class.java]
    }

    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ResourcesCompat.getColor(resources,
                R.color.colorPrimaryDark, null)
        }
    }

    private fun setUpSearchContent() {
        listGithubUser.layoutManager = LinearLayoutManager(this)
        viewModel.searchData.observe(this, Observer {
            searchData -> run {
                listGithubUser.adapter =
                    GithubUserRecyclerAdapter(
                        this,
                        searchData.toList()
                    )
            }
        })

        viewModel.searchStatus.observe(this, Observer {
            searchStatus -> run {
                when(searchStatus) {
                    SearchStatus.AWAITING_INPUT -> run {
                        listGithubUser.visibility = View.GONE
                        textStatus.visibility = View.VISIBLE
                        textStatus.setText(resources.getString(R.string.search_start))
                    }
                    SearchStatus.LOADING -> run {
                        listGithubUser.visibility = View.GONE
                        textStatus.visibility = View.VISIBLE
                        textStatus.setText(resources.getString(R.string.search_loading))
                    }
                    SearchStatus.COMPLETE -> run {
                        listGithubUser.visibility = View.VISIBLE
                        textStatus.visibility = View.GONE
                    }
                    SearchStatus.NO_INPUT -> run {
                        listGithubUser.visibility = View.GONE
                        textStatus.visibility = View.VISIBLE
                        textStatus.setText(resources.getString(R.string.search_start))
                    }
                    SearchStatus.ERROR_403 -> run {
                        listGithubUser.visibility = View.GONE
                        textStatus.visibility = View.VISIBLE
                        textStatus.setText(R.string.search_error_403)
                    }
                    SearchStatus.ERROR_422 -> run {
                        listGithubUser.visibility = View.GONE
                        textStatus.visibility = View.VISIBLE
                        textStatus.setText(R.string.search_error_422)
                    }
                    SearchStatus.ERROR_UNKNOWN -> run {
                        listGithubUser.visibility = View.GONE
                        textStatus.visibility = View.VISIBLE
                        textStatus.setText(R.string.search_error_unknown)
                    }
                    SearchStatus.NOT_FOUND -> run {
                        listGithubUser.visibility = View.GONE
                        textStatus.visibility = View.VISIBLE
                        textStatus.setText(resources.getString(R.string.search_not_found))
                    }
                }
            }
        })
    }

    private fun setUpSearchInput() {
        val search_bar_placeholder : EditText = findViewById(R.id.searchInput)
        search_bar_placeholder.setHint(resources.getString(R.string.search_bar_placeholder))
        searchInput.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            private var timer = Timer()
            private val debounce: Long = 300

            override fun afterTextChanged(s: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask(){
                    override fun run() {
                        viewModel.getGithubUsersData(s.toString())
                    }
                }, debounce)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        listGithubUser.adapter?.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor()
        setUpSearchInput()
        setUpSearchContent()
    }
}