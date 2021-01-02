package mg.pulse.pointagecar.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import mg.pulse.pointagecar.R

class LoginActivity:BaseActivity(){

    private var loginInput:TextInputEditText? = null
    private var passwordInput:TextInputEditText? = null
    private var connectionBtn:MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Hiding Title bar of this activity screen */
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        /** Making this activity, full screen */
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
        initViews()
        initListeners()
    }

    private fun initViews(){
        loginInput = findViewById(R.id.login_input)
        passwordInput = findViewById(R.id.password_input)
        connectionBtn = findViewById(R.id.connection_btn)
    }

    private fun initListeners(){
        connectionBtn?.setOnClickListener(View.OnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        })
    }

}