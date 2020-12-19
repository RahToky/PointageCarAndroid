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
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.utils.NfcUtils
import mg.pulse.pointagecar.views.fragments.FragmentTag
import mg.pulse.pointagecar.views.fragments.PointageListFragment


class MainActivity : BaseActivity() {

    private var ramassageListFragment: PointageListFragment? = null
    private var livraisonListFragment: PointageListFragment? = null

    //NFC
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configToolbar(getString(R.string.app_name))
        configureDrawerLayout()
        configureNavigationView()
        showFirstFragment()
        initNfcAdapter()
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    override fun onPause() {
        disableNfcForegroundDispatch()
        super.onPause()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        when (id) {
            R.id.ramassageMenu -> this.showFragment(FragmentTag.RAMASSAGE)
            R.id.livraisonMenu -> this.showFragment(FragmentTag.LIVRAISON)
            else -> {
                this.showFragment(FragmentTag.RAMASSAGE)
            }
        }
        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

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
        if (this.livraisonListFragment == null) this.livraisonListFragment = PointageListFragment(
            this,
            FragmentTag.LIVRAISON
        )
        this.startTransactionFragment(this.livraisonListFragment!!)
    }

    private fun showRamassageListFragment() {
        setToolbarTitle(FragmentTag.RAMASSAGE.toString())
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

    private fun onTagTapped(superTagId: String, superTagData: String) {
        Toast.makeText(
            this,
            "superTagId=$superTagId \nsuperTagData=$superTagData",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val rawMsgs:Array<Parcelable> = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES) as Array<Parcelable>
            if (rawMsgs != null) {
                val messages:Array<NdefMessage?> = arrayOfNulls<NdefMessage>(rawMsgs.size)
                var data = ""
                for (i in rawMsgs.indices) {
                    messages[i] = rawMsgs[i] as NdefMessage
                    val records = messages[i]!!.records
                    for(record in records)
                        data += "\n"+NfcUtils.getPaylodText(record)+"\n"
                }
                Log.i("from tag: ", data)
                Toast.makeText(this, data, Toast.LENGTH_LONG).show()
            }
        }
    }
}