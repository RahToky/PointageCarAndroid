package mg.pulse.pointagecar.views.activities

import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import mg.pulse.pointagecar.R

open class BaseActivity : AppCompatActivity() {
    protected lateinit var toolbar: Toolbar
    protected var toolbarTitle: TextView? = null

    protected fun initToolbar(title: String) {

        toolbar = findViewById(R.id.custom_toolbar)
        setSupportActionBar(toolbar)
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        toolbarTitle?.text = title

    }
}