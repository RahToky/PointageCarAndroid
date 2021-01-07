package mg.pulse.pointagecar.views.dialogs

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.GradientDrawable
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
import mg.pulse.pointagecar.models.utils.getNameInitial

class PointingDetailDialog : DialogFragment() {

    private var matriculeTv: TextView? = null
    private var nomTv: TextView? = null
    private var prenomTv: TextView? = null
    private var profilInitial: TextView? = null
    private var telephoneTv: TextView? = null
    private var smsBtn:ImageButton? = null
    private var callBtn:ImageButton? = null

    private var collaborater: User? = null
    private var profilBg: GradientDrawable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.dialog_pointing_detail, container, false);
        initViews(rootView)
        initListeners(rootView)
        if(collaborater != null){
            displayCollaborater()
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

    private fun displayCollaborater(){
        matriculeTv?.text = collaborater?.matricule
        nomTv?.text = collaborater?.lastName?.toUpperCase()
        prenomTv?.text = collaborater?.firstName?.toLowerCase()?.capitalize()
        telephoneTv?.text = formatPhoneNumberMG(collaborater?.phoneNumber)
        profilInitial?.text = getNameInitial(collaborater?.firstName,collaborater?.lastName!!)
        profilInitial?.background = this.profilBg
    }

    private fun initListeners(view: View){
        callBtn?.setOnClickListener {
            var intent: Intent = Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:${collaborater?.phoneNumber}"))
            startActivity(intent);
        }

        smsBtn?.setOnClickListener {
            val uri = Uri.parse("smsto:${collaborater?.phoneNumber}")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "Bonjour ${collaborater?.lastName},\n")
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
        profilInitial = view.findViewById(R.id.profil_name_initial)
    }

    fun setCollaborateur(collaborateur: User,bg: GradientDrawable){
        this.collaborater = collaborateur
        this.profilBg = bg
    }

}