package mg.pulse.pointagecar.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import mg.pulse.pointagecar.R
import java.util.*
import kotlin.concurrent.schedule

class OkPointingDialog(var displayTime:Long = 700) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.dialog_ok_pointing, container, false);
        return rootView
    }

    override fun onStart() {
        super.onStart()
        Timer().schedule(displayTime){
            dismiss()
        }
    }

}