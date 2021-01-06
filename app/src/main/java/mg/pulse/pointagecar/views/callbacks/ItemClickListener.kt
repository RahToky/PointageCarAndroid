package mg.pulse.pointagecar.views.callbacks

import android.graphics.drawable.GradientDrawable

interface ItemClickListener {
    fun onClick(item:Any, bg: GradientDrawable)
}