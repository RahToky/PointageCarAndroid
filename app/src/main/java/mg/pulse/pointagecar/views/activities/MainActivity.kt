package mg.pulse.pointagecar.views.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.views.fragments.RamassageListFragment


class MainActivity : BaseActivity() {

    private var ramassageListFragment:RamassageListFragment ? = null
    val RAMASSAGE_LIST_FRAGMENT = 0
    val LIVRAISON_LIST_FRAGMENT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configToolbar("Main")
        configureDrawerLayout()
        configureNavigationView()
        showFirstFragment()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        when (id) {
            R.id.ramassageMenu -> this.showFragment(RAMASSAGE_LIST_FRAGMENT)
            R.id.livraisonMenu -> this.showFragment(LIVRAISON_LIST_FRAGMENT)
            else -> {
            }
        }
        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showFragment(fragmentIdentifier: Int) {
        when (fragmentIdentifier) {
            RAMASSAGE_LIST_FRAGMENT -> showRamassageListFragment()
            LIVRAISON_LIST_FRAGMENT -> showLivraisonListFragement()
            else -> {
            }
        }
    }

    private fun showRamassageListFragment() {
        if (this.ramassageListFragment == null) this.ramassageListFragment = RamassageListFragment.newInstance()
        this.startTransactionFragment(this.ramassageListFragment!!)
    }

    private fun showLivraisonListFragement() {
        if (this.ramassageListFragment == null) this.ramassageListFragment = RamassageListFragment.newInstance()
        this.startTransactionFragment(this.ramassageListFragment!!)
    }

    private fun startTransactionFragment(fragment: Fragment) {
        if (!fragment.isVisible()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_frame_layout, fragment).commit()
        }
    }

    private fun showFirstFragment() {
        val visibleFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_frame_layout)
        if (visibleFragment == null) {
            showFragment(RAMASSAGE_LIST_FRAGMENT)
            navigationView!!.menu.getItem(0).isChecked = true
        }
    }
}