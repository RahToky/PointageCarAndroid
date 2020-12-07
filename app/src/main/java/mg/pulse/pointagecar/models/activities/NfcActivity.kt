package mg.pulse.pointagecar.models.activities

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.tech.Ndef
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import mg.pulse.pointagecar.R


class NfcActivity: BaseActivity() {

    // VIEWS COMPONENTS
    private lateinit var nfcMessage:TextView

    // OTHERS
    private lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)
        initViewComponents()
        initOtherComponents()
        setSupportActionBar(toolbar)
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            nfcMessage.text = "NFC is disabled."
        } else {
            nfcMessage.text = "NFC ok"
        }

        handleIntent(getIntent());
    }

    private fun initViewComponents(){
        toolbar = findViewById(R.id.toolbar)
        nfcMessage = findViewById(R.id.nfcMessage)
    }

    private fun initOtherComponents(){
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    private fun handleIntent(intent: Intent){
        nfcMessage.text = "handleIntent"
    }


}