package mg.pulse.pointagecar.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Collaborateur
import mg.pulse.pointagecar.models.entities.Ramassage

class PointageAdapter() : RecyclerView.Adapter<PointageViewHolder>() {

    private var ramassageList: List<Ramassage> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointageViewHolder =
        PointageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_collaborateur, parent, false)
        )

    override fun onBindViewHolder(holder: PointageViewHolder, position: Int) =
        holder.setItem(ramassageList[position])

    override fun getItemCount(): Int = ramassageList.size

    fun updateList(list:List<Ramassage>){
        ramassageList = list
        notifyDataSetChanged()
    }
}


class PointageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var firstNameTv: TextView = itemView.findViewById(R.id.collaboFirstTv)
    private var lastNameTv: TextView = itemView.findViewById(R.id.collaboLastnameTv)
    private var checkHourTv: TextView = itemView.findViewById(R.id.collaboCheckHourTv)

    fun setItem(ramassage: Ramassage) {
        firstNameTv.text = ramassage.collaborateur.prenom
        lastNameTv.text = ramassage.collaborateur.nom
        checkHourTv.text = ramassage.heureRamassage
    }
}