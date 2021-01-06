package mg.pulse.pointagecar.views.dialogs

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.User
import mg.pulse.pointagecar.models.utils.formatPhoneNumberMG

class PointingDetailDialog : DialogFragment() {

    private var matriculeTv: TextView? = null
    private var nomTv: TextView? = null
    private var prenomTv: TextView? = null
    private var telephoneTv: TextView? = null
    private var smsBtn:ImageButton? = null
    private var callBtn:ImageButton? = null

    private var collaborateur: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.dialog_pointing_detail, container, false);
        initViews(rootView)
        initListeners(rootView)
        if(collaborateur != null){
            displayCollaborateur()
        }
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

    private fun displayCollaborateur(){
        matriculeTv?.text = collaborateur?.matricule
        nomTv?.text = collaborateur?.lastName?.toUpperCase()
        prenomTv?.text = collaborateur?.firstName?.toLowerCase()?.capitalize()
        telephoneTv?.text = formatPhoneNumberMG(collaborateur?.phoneNumber)
    }

    private fun initListeners(view: View){
        callBtn?.setOnClickListener {
            var intent: Intent = Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:${collaborateur?.phoneNumber}"))
            startActivity(intent);
        }

        smsBtn?.setOnClickListener {
            val uri = Uri.parse("smsto:${collaborateur?.phoneNumber}")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "Bonjour ${collaborateur?.lastName},\n")
            startActivity(intent)
        }
    }

    private fun initViews(view: View) {
        matriculeTv = view.findViewById(R.id.matricule_tv)
        nomTv = view.findViewById(R.id.nomTv)
        prenomTv = view.findViewById(R.id.prenomTv)
        telephoneTv = view.findViewById(R.id.telephoneTv)
        smsBtn = view.findViewById(R.id.smsBtn)
        callBtn = view.findViewById(R.id.callBtn)
    }

    fun setCollaborateur(collaborateur: User){
        this.collaborateur = collaborateur
    }

}