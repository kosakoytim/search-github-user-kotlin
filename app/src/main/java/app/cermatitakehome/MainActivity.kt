package app.cermatitakehome

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.os.Build
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ResourcesCompat.getColor(resources, R.color.colorPrimaryDark, null)
        }
    }

    private fun displaySearchContent() {
        // SET PLACEHOLDER
        val search_bar_placeholder : EditText = findViewById(R.id.searchInput)
        search_bar_placeholder.setHint(resources.getString(R.string.search_bar_placeholder))

        // SET RECYCLER VIEW
        val mockData = ArrayList<GithubUserModel>()
        mockData.add(GithubUserModel("test_1", "https://github.com/identicons/test1.png"))
        mockData.add(GithubUserModel("test_2", "https://github.com/identicons/test2.png"))
        mockData.add(GithubUserModel("test_3", "https://github.com/identicons/test3.png"))
        mockData.add(GithubUserModel("test_4", "https://github.com/identicons/test4.png"))
        mockData.add(GithubUserModel("test_5", "https://github.com/identicons/test5.png"))
        mockData.add(GithubUserModel("test_6", "https://github.com/identicons/test6.png"))
        mockData.add(GithubUserModel("test_7", "https://github.com/identicons/test7.png"))
        mockData.add(GithubUserModel("test_8", "https://github.com/identicons/test8.png"))
        mockData.add(GithubUserModel("test_9", "https://github.com/identicons/test9.png"))
        mockData.add(GithubUserModel("test_10", "https://github.com/identicons/test10.png"))
        mockData.add(GithubUserModel("test_11", "https://github.com/identicons/test11.png"))
        mockData.add(GithubUserModel("test_12", "https://github.com/identicons/test12.png"))


        listGithubUser.layoutManager = LinearLayoutManager(this)
        listGithubUser.adapter = GithubUserRecyclerAdapter(this, mockData.toList())
    }

    override fun onResume() {
        super.onResume()
        listGithubUser.adapter?.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor()
        displaySearchContent()
    }
}