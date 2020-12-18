package mg.pulse.pointagecar.views.activities

import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import mg.pulse.pointagecar.R


open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    protected lateinit var toolbar: Toolbar
    protected var toolbarTitle: TextView? = null
    protected var drawerLayout: DrawerLayout? = null
    protected var navigationView: NavigationView? = null

    protected fun configToolbar(res:Int, title: String) {
        toolbar = findViewById(res)
        setSupportActionBar(toolbar)
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        toolbarTitle?.text = title
    }

    protected fun configToolbar(title: String? = "") {
        toolbar = findViewById(R.id.custom_toolbar)
        setSupportActionBar(toolbar)
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        toolbarTitle?.text = title
    }

    protected fun setToolbarTitle(title:String){
        toolbarTitle?.text = title
    }

    protected open fun configureDrawerLayout() {
        drawerLayout = findViewById(R.id.activity_main_drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
    }

    // 3 - Configure NavigationView
    protected open fun configureNavigationView() {
        navigationView = findViewById(R.id.activity_main_nav_view)
        navigationView = findViewById<View>(R.id.activity_main_nav_view) as NavigationView
        navigationView?.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}