package mg.pulse.pointagecar.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Ramassage
import mg.pulse.pointagecar.views.callbacks.ItemClickListener

class PointageAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<PointageViewHolder>() {

    private var ramassageList: List<Ramassage> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointageViewHolder =
        PointageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pointage, parent, false)
        )

    override fun onBindViewHolder(holder: PointageViewHolder, position: Int) =
        holder.setItem(ramassageList[position],itemClickListener)

    override fun getItemCount(): Int = ramassageList.size

    fun updateList(list: List<Ramassage>) {
        ramassageList = list
        notifyDataSetChanged()
    }
}

class PointageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var matriculeTv: TextView = itemView.findViewById(R.id.matriculeTv)
    private var fullNameTv: TextView = itemView.findViewById(R.id.fullNameTv)
    private var checkHourTv: TextView = itemView.findViewById(R.id.collaboCheckHourTv)
    private val itemLayout: LinearLayout = itemView.findViewById(R.id.itemLayout)

    fun setItem(ramassage: Ramassage, itemClickListener:ItemClickListener) {
        matriculeTv.text = ramassage.collaborateur.matricule
        fullNameTv.text = ramassage.collaborateur.fullName
        checkHourTv.text = ramassage.heureRamassage.subSequence(0, 5)
        itemLayout.setOnClickListener(View.OnClickListener {
            itemClickListener.onClick(ramassage)
        })
    }
}