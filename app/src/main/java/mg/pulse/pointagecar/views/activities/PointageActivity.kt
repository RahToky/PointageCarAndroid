package mg.pulse.pointagecar.views.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Collaborateur
import mg.pulse.pointagecar.remote.services.CollaboAPIRepository
import mg.pulse.pointagecar.viewmodels.PointageViewModel
import mg.pulse.pointagecar.views.PointageAdapter

class PointageActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pointageAdapter: PointageAdapter
    private val pointageViewModel: PointageViewModel = PointageViewModel()

    private lateinit var collaboList: List<Collaborateur>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pointage)
        initToolbar(resources.getString(R.string.pointage_du_jour))
        initViews()

        pointageViewModel.initAPI(this)
        getCollabo()

        /*findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

        }*/
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        pointageAdapter = PointageAdapter()
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = this.pointageAdapter
    }

    private fun getCollabo() {
        pointageViewModel.getAllCollabo().observe(this, Observer {
            pointageAdapter.updateList(it) }
        )
    }
}