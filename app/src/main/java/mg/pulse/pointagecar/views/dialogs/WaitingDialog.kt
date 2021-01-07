package mg.pulse.pointagecar.views.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import mg.pulse.pointagecar.R

class WaitingDialog : DialogFragment() {

    private var waitingMessage:TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.dialog_waiting, container, false);
        initViews(rootView)
        return rootView
    }

    override fun onStart() {
        super.onStart()
        var mDialog: Dialog? = getDialog()
        if (mDialog != null) {
            mDialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun initViews(view: View) {
        waitingMessage = view.findViewById(R.id.message_waiting_text)
    }

    fun displayMessage(message:String){
        waitingMessage?.text = message
    }
}