package mg.pulse.pointagecar.models.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.viewmodels.CollaboViewModel
import mg.pulse.pointagecar.views.PointageAdapter
import java.security.AccessController.getContext

class PointageActivity:BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pointageAdapter: PointageAdapter
    private var collaboViewModel:CollaboViewModel = CollaboViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pointage)
        initToolbar(resources.getString(R.string.pointage_du_jour))
        initViews()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

        }
    }

    private fun initViews(){
        recyclerView = findViewById(R.id.recyclerView)
        pointageAdapter = PointageAdapter(collaboViewModel.getCollaboList())
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = this.pointageAdapter
    }

}