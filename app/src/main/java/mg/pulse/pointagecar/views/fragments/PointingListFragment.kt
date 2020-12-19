package mg.pulse.pointagecar.views.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import mg.pulse.pointagecar.App
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Pointing
import mg.pulse.pointagecar.models.utils.isNetworkConnected
import mg.pulse.pointagecar.models.utils.toStringLeadingZero
import mg.pulse.pointagecar.models.utils.toastNetworkNotConnected
import mg.pulse.pointagecar.viewmodels.PointingViewModel
import mg.pulse.pointagecar.views.PointageAdapter
import mg.pulse.pointagecar.views.callbacks.ItemClickListener
import mg.pulse.pointagecar.views.dialogs.PointingDetailDialog
import java.text.SimpleDateFormat
import java.util.*

class PointageListFragment(var lifeCycleOwner:LifecycleOwner, val fragmentTag:FragmentTag): Fragment() , ItemClickListener {

    private val CAR_ID: String = "1"
    private lateinit var recyclerView: RecyclerView
    private lateinit var pointageAdapter: PointageAdapter
    private val pointageViewModel: PointingViewModel = PointingViewModel()
    private lateinit var ramassageSizeTv: TextView
    private lateinit var dateRamassageTv: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var pointageDetailDialog: PointingDetailDialog = PointingDetailDialog()
    private lateinit var calendarLayout: LinearLayout
    private lateinit var mContext:Context

    companion object{
        fun newInstance(lifeCycleOwner:LifecycleOwner, fragmentTag: FragmentTag):PointageListFragment = PointageListFragment(lifeCycleOwner,fragmentTag)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_pointage_list, container, false);
        initViews(rootView)
        initListeners()
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateRamassageTv.text = simpleDateFormat.format(Date())

        if (isNetworkConnected(activity as Activity))
            initPointingList()
        else
            toastNetworkNotConnected(activity as Activity)

        handleViewModelsError()
        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun initListeners(){
        swipeRefreshLayout.setOnRefreshListener{
            initPointingList()
        }

        calendarLayout.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                mContext,{ _, year, monthOfYear, dayOfMonth ->
                    dateRamassageTv.text = "$year-${ toStringLeadingZero(monthOfYear + 1)}-${toStringLeadingZero(dayOfMonth)}"
                    initPointingList()
                },year,month,day
            )
            dpd.show()
        }
    }

    private fun initPointingList(){
        swipeRefreshLayout.isRefreshing = true
        if(fragmentTag == FragmentTag.RAMASSAGE)
            getRamassageList()
        else
            getLivraisonList()
    }

    private fun initViews(view: View) {
        calendarLayout = view.findViewById(R.id.calendarLayout)
        dateRamassageTv = view.findViewById(R.id.dateRamassageTv)
        ramassageSizeTv = view.findViewById(R.id.ramassageSizeTv)
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout)
        pointageAdapter = PointageAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = this.pointageAdapter
    }

    private fun getRamassageList() {
        pointageViewModel.initRamassageList(CAR_ID, dateRamassageTv.text.toString())
        pointageViewModel.pickupPointingList.observe(lifeCycleOwner, {
            pointageAdapter.updateList(it)
            ramassageSizeTv.text = it.size.toString()
            swipeRefreshLayout.isRefreshing = false
        })
    }

    private fun getLivraisonList(){
        pointageViewModel.initLivraisonList(CAR_ID, dateRamassageTv.text.toString())
        pointageViewModel.deliveryPointingList.observe(lifeCycleOwner, {
            pointageAdapter.updateList(it)
            ramassageSizeTv.text = it.size.toString()
            swipeRefreshLayout.isRefreshing = false
        })
    }

    private fun handleViewModelsError(){
        pointageViewModel.errorMessage?.observe(lifeCycleOwner,{
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
        })
    }

    override fun onClick(item: Any) {
        var ramassage: Pointing = item as Pointing
        pointageDetailDialog.setCollaborateur(ramassage.collaborater)
        pointageDetailDialog.show(activity?.supportFragmentManager!!, null)
    }

}

enum class FragmentTag(value:Int){
    RAMASSAGE(R.string.ramassage){
        override fun toString(): String {
            return App.getInstance()?.resources?.getString(R.string.ramassage)?:""
        }
    },
    LIVRAISON(R.string.livraison){
        override fun toString(): String {
            return App.getInstance()?.resources?.getString(R.string.livraison) ?: ""
        }
    }
}