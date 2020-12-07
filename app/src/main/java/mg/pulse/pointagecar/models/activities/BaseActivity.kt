package mg.pulse.pointagecar.models.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity:AppCompatActivity() {
    protected lateinit var toolbar: Toolbar
}