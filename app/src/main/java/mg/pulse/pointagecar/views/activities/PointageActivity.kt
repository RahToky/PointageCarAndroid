package mg.pulse.pointagecar.views.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.LinearLayout
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
    private lateinit var calendarLayout:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pointage)
        configToolbar(resources.getString(R.string.ramassage))
        initViews()
        initListeners()
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateRamassageTv.text = simpleDateFormat.format(Date())
        getCurrentRamassage()
    }

    private fun initListeners(){
        swipeRefreshLayout.setOnRefreshListener{
            swipeRefreshLayout.isRefreshing = true
            getCurrentRamassage()
        }

        calendarLayout.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this@PointageActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                dateRamassageTv.text = "$year-${monthOfYear+1}-$dayOfMonth"
                getCurrentRamassage()
            }, year, month, day)
            dpd.show()
        }
    }

    private fun initViews() {
        calendarLayout = findViewById(R.id.calendarLayout)
        dateRamassageTv = findViewById(R.id.dateRamassageTv)
        ramassageSizeTv = findViewById(R.id.ramassageSizeTv)
        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipe_layout)
        pointageAdapter = PointageAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = this.pointageAdapter
    }

    private fun getCurrentRamassage() {
        pointageViewModel.initAPI(CAR_ID, dateRamassageTv.text.toString())
        pointageViewModel.getRamassages().observe(this, Observer {
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