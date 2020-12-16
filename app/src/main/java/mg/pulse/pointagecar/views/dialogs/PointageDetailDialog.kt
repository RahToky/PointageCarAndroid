package mg.pulse.pointagecar.views.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Collaborateur
import mg.pulse.pointagecar.models.utils.formatPhoneNumberMG

class PointageDetailDialog : DialogFragment() {

    private var matriculeTv: TextView? = null
    private var nomTv: TextView? = null
    private var prenomTv: TextView? = null
    private var telephoneTv: TextView? = null

    private var collaborateur: Collaborateur? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.dialog_pointage_detail, container, false);
        initViews(rootView)
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
        nomTv?.text = collaborateur?.nom
        prenomTv?.text = collaborateur?.prenom
        telephoneTv?.text = formatPhoneNumberMG(collaborateur?.telephone)
    }

    private fun initViews(view: View) {
        matriculeTv = view.findViewById(R.id.matriculeTv)
        nomTv = view.findViewById(R.id.nomTv)
        prenomTv = view.findViewById(R.id.prenomTv)
        telephoneTv = view.findViewById(R.id.telephoneTv)
    }

    fun setCollaborateur(collaborateur: Collaborateur){
        this.collaborateur = collaborateur
    }

}