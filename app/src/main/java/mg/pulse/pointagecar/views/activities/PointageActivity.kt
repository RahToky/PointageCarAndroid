package mg.pulse.pointagecar.views.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Ramassage
import mg.pulse.pointagecar.viewmodels.PointageViewModel
import mg.pulse.pointagecar.views.PointageAdapter
import mg.pulse.pointagecar.views.callbacks.ItemClickListener
import mg.pulse.pointagecar.views.dialogs.PointageDetailDialog
import java.text.SimpleDateFormat
import java.util.*

class PointageActivity : BaseActivity() , ItemClickListener{

    private val CAR_ID: String = "1"
    private lateinit var recyclerView: RecyclerView
    private lateinit var pointageAdapter: PointageAdapter
    private val pointageViewModel: PointageViewModel = PointageViewModel()
    private lateinit var ramassageSizeTv: TextView
    private lateinit var dateRamassageTv: TextView
    private lateinit var swipeRefreshLayout:SwipeRefreshLayout
    private var pointageDetailDialog:PointageDetailDialog = PointageDetailDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pointage)
        initToolbar(resources.getString(R.string.ramassage))
        initViews()
        initListeners()
        getCurrentRamassage()
        var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        dateRamassageTv.text = simpleDateFormat.format(Date())

    }

    private fun initListeners(){
        swipeRefreshLayout.setOnRefreshListener{
            swipeRefreshLayout.isRefreshing = true
            getCurrentRamassage()
        }
    }

    private fun initViews() {
        ramassageSizeTv = findViewById(R.id.ramassageSizeTv)
        dateRamassageTv = findViewById(R.id.dateRamassageTv)
        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipe_layout)
        pointageAdapter = PointageAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = this.pointageAdapter
    }

    private fun getCurrentRamassage() {
        pointageViewModel.initAPI(CAR_ID)
        pointageViewModel.getCurrentRamassages().observe(this, Observer {
            pointageAdapter.updateList(it)
            ramassageSizeTv.text = it.size.toString()
            swipeRefreshLayout.isRefreshing = false
        })
    }

    override fun onClick(item: Any) {
        var ramassage:Ramassage = item as Ramassage
        pointageDetailDialog.setCollaborateur(ramassage.collaborateur)
        pointageDetailDialog.show(supportFragmentManager,null)
    }
}