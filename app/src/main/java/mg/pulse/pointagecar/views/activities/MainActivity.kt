package mg.pulse.pointagecar.views.activities

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Car
import mg.pulse.pointagecar.models.entities.User
import mg.pulse.pointagecar.models.utils.NfcUtils
import mg.pulse.pointagecar.models.utils.SessionManager
import mg.pulse.pointagecar.viewmodels.MainViewModel
import mg.pulse.pointagecar.views.dialogs.OkPointingDialog
import mg.pulse.pointagecar.views.fragments.FragmentTag
import mg.pulse.pointagecar.views.fragments.PointageListFragment


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mainViewModel: MainViewModel = MainViewModel()
    private var sessionManager:SessionManager? = null
    protected lateinit var toolbar: Toolbar
    protected var toolbarTitle: TextView? = null
    protected var drawerLayout: DrawerLayout? = null
    protected var navigationView: NavigationView? = null
    private var driverFirstNameTv: TextView? = null
    private var driverLastNameTv: TextView? = null
    private var carImmatriculationTv: TextView? = null
    private var ramassageListFragment: PointageListFragment? = null
    private var livraisonListFragment: PointageListFragment? = null
    private val okPointingDialog: OkPointingDialog = OkPointingDialog()
    private var activeFragment:FragmentTag = FragmentTag.RAMASSAGE

    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionManager = SessionManager(this)
        configToolbar(getString(R.string.app_name))
        configureDrawerLayout()
        configureNavigationView()
        showFirstFragment()
        initNfcAdapter()
        displayDriver()
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    override fun onPause() {
        disableNfcForegroundDispatch()
        super.onPause()
    }


    /* =========================================== *\
                       TOOLBAR
    \*=============================================*/

    protected fun configToolbar(res: Int, title: String) {
        toolbar = findViewById(res)
        setSupportActionBar(toolbar)
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        toolbarTitle?.text = title
    }

    protected fun configToolbar(title: String? = "") = configToolbar(R.id.custom_toolbar, title!!)
    protected fun setToolbarTitle(title: String) {
        toolbarTitle?.text = title
    }


    /* =========================================== *\
                    NAVIGATION DRAWER
    \*=============================================*/

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

    protected open fun configureNavigationView() {
        navigationView = findViewById(R.id.activity_main_nav_view)
        navigationView?.setNavigationItemSelectedListener(this)

        driverFirstNameTv = navigationView!!.getHeaderView(0).findViewById(R.id.drawerDriverFirstNameTv)
        driverLastNameTv = navigationView!!.getHeaderView(0).findViewById(R.id.drawerDriverLastNameTv)
        carImmatriculationTv = navigationView!!.getHeaderView(0).findViewById(R.id.drawerCarImmatriculationTv)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        when (id) {
            R.id.ramassageMenu -> this.showFragment(FragmentTag.RAMASSAGE)
            R.id.livraisonMenu -> this.showFragment(FragmentTag.LIVRAISON)
            else -> {
                logout()
            }
        }
        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }


    /* =========================================== *\
                       FRAGMENTS
    \*=============================================*/

    private fun showFragment(fragmentTag: FragmentTag) {
        when (fragmentTag) {
            FragmentTag.RAMASSAGE -> showRamassageListFragment()
            FragmentTag.LIVRAISON -> showLivraisonListFragement()
            else -> {
                showFirstFragment()
            }
        }
    }

    private fun showFirstFragment() {
        val visibleFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_frame_layout)
        if (visibleFragment == null) {
            showFragment(FragmentTag.RAMASSAGE)
            navigationView!!.menu.getItem(0).isChecked = true
        }
    }

    private fun showLivraisonListFragement() {
        setToolbarTitle(FragmentTag.LIVRAISON.toString())
        activeFragment = FragmentTag.LIVRAISON
        if (this.livraisonListFragment == null) this.livraisonListFragment = PointageListFragment(
            this,
            FragmentTag.LIVRAISON
        )
        this.startTransactionFragment(this.livraisonListFragment!!)
    }

    private fun showRamassageListFragment() {
        setToolbarTitle(FragmentTag.RAMASSAGE.toString())
        activeFragment = FragmentTag.RAMASSAGE
        if (this.ramassageListFragment == null) this.ramassageListFragment = PointageListFragment(
            this,
            FragmentTag.RAMASSAGE
        )
        this.startTransactionFragment(this.ramassageListFragment!!)
    }

    private fun startTransactionFragment(fragment: Fragment) {
        if (!fragment.isVisible()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_frame_layout, fragment).commit()
        }
    }

    private fun displayDriver() {
        driverFirstNameTv?.text = SessionManager(this).getUserFirstName()?.toLowerCase()?.capitalize()
        driverLastNameTv?.text = SessionManager(this).getUserLastName()
    }


    /* =========================================== *\
                        FOR NFC
    \*=============================================*/

    private fun initNfcAdapter() {
        nfcAdapter = (getSystemService(Context.NFC_SERVICE) as NfcManager).defaultAdapter
    }

    private fun enableNfcForegroundDispatch() {
        try {
            val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val nfcPendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            nfcAdapter?.enableForegroundDispatch(
                this,
                nfcPendingIntent,
                NfcUtils.getIntentFilters(),
                null
            )
        } catch (ex: IllegalStateException) {
            Log.e("MyTag", "Error enabling NFC foreground dispatch", ex)
        }
    }

    private fun disableNfcForegroundDispatch() {
        try {
            nfcAdapter?.disableForegroundDispatch(this)
        } catch (ex: IllegalStateException) {
            Log.e("MyTag", "Error disabling NFC foreground dispatch", ex)
        }
    }

    private fun displayOkPointingDialog() {
        if (!okPointingDialog.isAdded)
            okPointingDialog.show(supportFragmentManager, "checked")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val rawMsgs: Array<Parcelable> =
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES) as Array<Parcelable>
            if (rawMsgs != null) {
                val messages: Array<NdefMessage?> = arrayOfNulls<NdefMessage>(rawMsgs.size)
                var matricule = ""
                for (i in rawMsgs.indices) {
                    messages[i] = rawMsgs[i] as NdefMessage
                    val records = messages[i]!!.records
                    for (record in records)
                        matricule += "\n" + NfcUtils.getPaylodText(record) + "\n"
                }
                getCollaboInfoAndCheckPointing(matricule)
            }
        }
    }

    private fun getCollaboInfoAndCheckPointing(matricule:String){
        mainViewModel.initCollaboraterByMatricule(matricule)
        mainViewModel.collaborater.observe(this, {
            checkPointing(it)
        })
    }

    private fun checkPointing(collaborater: User){
        val car = Car(sessionManager?.getCarId()!!,sessionManager?.getCarImmatriculation()!!,User(sessionManager?.getUserId(),sessionManager?.getUserMatricule()))
        when(activeFragment){
            FragmentTag.RAMASSAGE -> mainViewModel.savePickupPointing(collaborater, car)
            else -> mainViewModel.saveDeliveryPointing(collaborater,car)
        }
        displayOkPointingDialog()
    }

    private fun logout(){
        SessionManager(this).clear()
        val mainIntent = Intent(this, LoginActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
}