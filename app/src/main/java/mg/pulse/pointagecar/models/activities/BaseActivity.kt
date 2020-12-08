package mg.pulse.pointagecar.models.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import mg.pulse.pointagecar.R

open class BaseActivity:AppCompatActivity() {
    protected lateinit var toolbar: Toolbar

    protected fun initToolbar(title:String){
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(findViewById(R.id.toolbar))
        toolbar.title = title
    }
}