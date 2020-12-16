package mg.pulse.pointagecar.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mg.pulse.pointagecar.R

class RamassageListFragment: Fragment() {

    companion object{
        fun newInstance():RamassageListFragment = RamassageListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ramassage_list, container, false);
    }

}