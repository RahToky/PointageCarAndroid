package mg.pulse.pointagecar.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import mg.pulse.pointagecar.R
import java.util.*
import kotlin.concurrent.schedule


class OkPointingDialog(var displayTime: Long = 1000) : DialogFragment() {

    private var okPointingLogo:ImageView? = null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.dialog_ok_pointing, container, false);
        initViews(rootView)
        startLogoAnim()
        return rootView
    }

    override fun onStart() {
        super.onStart()
        Timer().schedule(displayTime){
            dismiss()
        }
    }

    fun initViews(view:View) { okPointingLogo = view.findViewById(R.id.okPointingLogo)}

    fun startLogoAnim() = okPointingLogo?.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.ok_pointing))

}