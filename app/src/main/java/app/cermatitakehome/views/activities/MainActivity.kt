package app.cermatitakehome.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import app.cermatitakehome.views.adapters.GithubUserRecyclerAdapter
import app.cermatitakehome.R
import app.cermatitakehome.models.GithubUserSearchItemModel
import app.cermatitakehome.viewModels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.github_user_item.*
import kotlinx.android.synthetic.main.search_bar.*


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
                Log.d("MAIN_ACTIVITY", "HERE: " + searchData.toString())
                listGithubUser.adapter =
                    GithubUserRecyclerAdapter(
                        this,
                        searchData.toList()
                    )
            }
        })
    }

    private fun setUpSearchInput() {
        val search_bar_placeholder : EditText = findViewById(R.id.searchInput)
        search_bar_placeholder.setHint(resources.getString(R.string.search_bar_placeholder))
        searchInput.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getGithubUsersData(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}
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