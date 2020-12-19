package mg.pulse.pointagecar.models.utils

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Parcelable
import android.util.Log
import com.google.common.io.BaseEncoding
import kotlin.experimental.and

class NfcUtils {
    companion object {
        private val mimeType: String = ""

        fun getUID(intent: Intent): String {
            val myTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            return BaseEncoding.base16().encode(myTag?.id)
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

        fun getIntentFilters(): Array<IntentFilter> {
            val ndefFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
            try {
                ndefFilter.addDataType("text/plain")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                Log.e("NfcUtils", "Problem in parsing mime type for nfc reading", e)
            }

            return arrayOf(ndefFilter)
        }

        fun prepareMessageToWrite(tagData: String, context: Context): NdefMessage {
            val message: NdefMessage
            val typeBytes = mimeType.toByteArray()
            val payload = tagData.toByteArray()
            val record1 = NdefRecord(NdefRecord.TNF_MIME_MEDIA, typeBytes, null, payload)
            val record2 = NdefRecord.createApplicationRecord(context.packageName)
            message = NdefMessage(arrayOf(record1, record2))
            return message
        }

        fun getPaylodText(record: NdefRecord): String {
            val payload: ByteArray = record.payload

            val textEncoding = if (payload[0] and 128.toByte() == 0.toByte()) "UTF-8" else "UTF-16"

            //Get the Language Code
            val languageCodeLength: Int = (payload[0] and 52.toByte()).toInt()
            //val languageCode = String(payload, 1, languageCodeLength, charset("US-ASCII"))

            //Get the Text
            return String(
                payload,
                languageCodeLength,
                payload.size - languageCodeLength,
                charset(textEncoding)
            )
        }
    }
}
