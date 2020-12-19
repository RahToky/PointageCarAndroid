package mg.pulse.pointagecar.views.activities

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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

    private var ramassageListFragment:PointageListFragment ? = null
    private var livraisonListFragment:PointageListFragment ? = null

    //NFC
    private var nfcAdapter:NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configToolbar("Main")
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
            nfcAdapter?.enableForegroundDispatch(this, nfcPendingIntent, null, null)
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

    fun getIntentFilters(): Array<IntentFilter> {
        val ndefFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            ndefFilter.addDataType("application/vnd.com.tickets")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            Log.e("NfcUtils", "Problem in parsing mime type for nfc reading", e)
        }

        return arrayOf(ndefFilter)
    }

    fun getData(rawMsgs: Array<Parcelable>): String {
        val msgs = arrayOfNulls<NdefMessage>(rawMsgs.size)
        for (i in rawMsgs.indices) {
            msgs[i] = rawMsgs[i] as NdefMessage
        }

        val records = msgs[0]!!.records

        var recordData = ""

        for (record in records) {
            recordData += record.toString() + "\n"
        }

        return recordData
    }

    private fun onTagTapped(superTagId: String, superTagData: String) {
        Toast.makeText(this,"superTagId=$superTagId \nsuperTagData=$superTagData",Toast.LENGTH_SHORT).show()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMsgs != null) {
                onTagTapped(NfcUtils.getUID(intent), NfcUtils.getData(rawMsgs))
            }
        }
    }
}