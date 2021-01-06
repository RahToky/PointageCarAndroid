package mg.pulse.pointagecar.views.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Pointing
import mg.pulse.pointagecar.models.utils.getNameInitial
import mg.pulse.pointagecar.views.callbacks.ItemClickListener


class PointageAdapter(val itemClickListener: ItemClickListener, val colorList:IntArray) : RecyclerView.Adapter<PointageViewHolder>() {

    private var ramassageList: List<Pointing> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointageViewHolder = PointageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pointing, parent, false))

    override fun onBindViewHolder(holder: PointageViewHolder, position: Int) = holder.setItem(ramassageList[position], colorList[(colorList.indices-1).random()], itemClickListener)

    override fun getItemCount(): Int = ramassageList.size

    fun updateList(list: List<Pointing>) {
        ramassageList = list
        notifyDataSetChanged()
    }
}

class PointageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var profilNameInitial: TextView = itemView.findViewById(R.id.profil_name_initial)
    private var matriculeTv: TextView = itemView.findViewById(R.id.matricule_tv)
    private var fullNameTv: TextView = itemView.findViewById(R.id.fullname_tv)
    private var checkHourTv: TextView = itemView.findViewById(R.id.pointing_time_tv)
    private val itemLayout: LinearLayout = itemView.findViewById(R.id.item_layout)

    fun setItem(ramassage: Pointing, bgColor: Int, itemClickListener: ItemClickListener) {val draw = GradientDrawable()
        draw.shape = GradientDrawable.OVAL
        draw.setColor(bgColor)
        profilNameInitial.text = getNameInitial(ramassage.collaborater?.firstName,ramassage.collaborater?.lastName?:"")
        profilNameInitial.background = draw
        matriculeTv.text = ramassage.collaborater?.matricule
        fullNameTv.text = ramassage.collaborater?.lastName
        checkHourTv.text = ramassage.pointingHour?.subSequence(0, 5)
        itemLayout.setOnClickListener(View.OnClickListener {
            itemClickListener.onClick(ramassage)
        })
    }
}