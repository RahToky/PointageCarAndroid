package mg.pulse.pointagecar.views.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Collaborateur
import mg.pulse.pointagecar.viewmodels.PointageViewModel
import mg.pulse.pointagecar.views.PointageAdapter
import java.text.SimpleDateFormat
import java.util.*

class PointageActivity : BaseActivity() {

    private val CAR_ID: String = "1"
    private lateinit var recyclerView: RecyclerView
    private lateinit var pointageAdapter: PointageAdapter
    private val pointageViewModel: PointageViewModel = PointageViewModel()
    private lateinit var ramassageSizeTv: TextView
    private lateinit var dateRamassageTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pointage)
        initToolbar(resources.getString(R.string.pointage_du_jour))
        initViews()
        pointageViewModel.initAPI(CAR_ID)
        getCurrentRamassage()
        var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        dateRamassageTv.text = simpleDateFormat.format(Date())

    }

    private fun initViews() {
        ramassageSizeTv = findViewById(R.id.ramassageSizeTv)
        dateRamassageTv = findViewById(R.id.dateRamassageTv)
        recyclerView = findViewById(R.id.recyclerView)
        pointageAdapter = PointageAdapter()
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = this.pointageAdapter
    }

    private fun getCurrentRamassage() {
        pointageViewModel.getCurrentRamassage().observe(this, Observer {
            pointageAdapter.updateList(it)
            ramassageSizeTv.text = it.size.toString()
        })
    }
}