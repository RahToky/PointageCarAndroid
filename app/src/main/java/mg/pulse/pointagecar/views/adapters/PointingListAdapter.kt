package mg.pulse.pointagecar.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Pointing
import mg.pulse.pointagecar.views.callbacks.ItemClickListener

class PointageAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<PointageViewHolder>() {

    private var ramassageList: List<Pointing> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointageViewHolder =
        PointageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pointing, parent, false)
        )

    override fun onBindViewHolder(holder: PointageViewHolder, position: Int) =
        holder.setItem(ramassageList[position],itemClickListener)

    override fun getItemCount(): Int = ramassageList.size

    fun updateList(list: List<Pointing>) {
        ramassageList = list
        notifyDataSetChanged()
    }
}

class PointageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var matriculeTv: TextView = itemView.findViewById(R.id.matriculeTv)
    private var fullNameTv: TextView = itemView.findViewById(R.id.fullNameTv)
    private var checkHourTv: TextView = itemView.findViewById(R.id.collaboCheckHourTv)
    private val itemLayout: LinearLayout = itemView.findViewById(R.id.itemLayout)

    fun setItem(ramassage: Pointing, itemClickListener:ItemClickListener) {
        matriculeTv.text = ramassage.collaborater.matricule
        fullNameTv.text = ramassage.collaborater.fullName
        checkHourTv.text = ramassage.pointingHour.subSequence(0, 5)
        itemLayout.setOnClickListener(View.OnClickListener {
            itemClickListener.onClick(ramassage)
        })
    }
}