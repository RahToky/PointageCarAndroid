package mg.pulse.pointagecar.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Collaborateur
import java.lang.ref.WeakReference

class PointageAdapter(collaborateurList: List<Collaborateur>) : RecyclerView.Adapter<PointageViewHolder>() {

    private val collaboList: List<Collaborateur> = collaborateurList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointageViewHolder =
        PointageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_collaborateur, parent, false)
        )

    override fun onBindViewHolder(holder: PointageViewHolder, position: Int) =
        holder.setItem(collaboList.get(position), "06:0${position + 2}")

    override fun getItemCount(): Int = collaboList.size
}


class PointageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var firstNameTv: TextView = itemView.findViewById(R.id.collaboFirstTv)
    private var lastNameTv: TextView = itemView.findViewById(R.id.collaboLastnameTv)
    private var checkHourTv: TextView = itemView.findViewById(R.id.collaboCheckHourTv)

    fun setItem(collabo: Collaborateur, hours: String) {
        firstNameTv.text = collabo.firstName
        lastNameTv.text = collabo.lastName
        checkHourTv.text = hours
    }
}