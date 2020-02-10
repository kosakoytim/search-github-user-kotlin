package app.cermatitakehome

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.os.Build
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat


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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor()
        displaySearchContent()
    }
}