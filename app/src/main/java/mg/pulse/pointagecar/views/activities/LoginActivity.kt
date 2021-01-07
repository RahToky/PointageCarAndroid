package mg.pulse.pointagecar.views.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.utils.SessionManager
import mg.pulse.pointagecar.viewmodels.AuthViewModel
import android.text.TextWatcher as TextWatcher1

class LoginActivity : BaseActivity() {

    private var authAccessLayout: LinearLayout? = null
    private var authProgressLayout: LinearLayout? = null
    private var loginInput: TextInputEditText? = null
    private var passwordInput: TextInputEditText? = null
    private var connectionBtn: MaterialButton? = null
    private var errorTv: TextView? = null
    private var sessionManager:SessionManager? = null

    private val CAR_ID:String = "1"

    private var authViewModel: AuthViewModel = AuthViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(R.layout.activity_login)
        sessionManager = SessionManager(this)
        initViews()
        initListeners()
        isLogged()
        isCarExist()
        handleViewModelsError()
    }

    private fun initViews() {
        authAccessLayout = findViewById(R.id.auth_access_layout)
        authProgressLayout = findViewById(R.id.auth_progress_layout)
        loginInput = findViewById(R.id.login_input)
        passwordInput = findViewById(R.id.password_input)
        connectionBtn = findViewById(R.id.connection_btn)
        errorTv = findViewById(R.id.error_text)
    }

    private fun initListeners() {
        loginInput?.onFocusChangeListener = inputFocusListener
        passwordInput?.onFocusChangeListener = inputFocusListener
        connectionBtn?.setOnClickListener(View.OnClickListener {
            if (loginInput?.text.toString()?.trim().isNotEmpty() && passwordInput?.text.toString()
                    ?.trim().isNotEmpty()
            ) {
                showProgress(true)
                authViewModel.authentificate(
                    loginInput?.text.toString()?.trim(),
                    passwordInput?.text.toString()?.trim()
                )
            } else {
                setErrorMessage(resources.getString(R.string.auth_empty_field_error))
            }
        })
    }

    private val inputFocusListener = object : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            setErrorMessage(null)
        }
    }

    private fun handleViewModelsError() {
        authViewModel.errorMessage?.observe(this, {
            showProgress(false)
            if (it.contains("401", false))
                setErrorMessage(resources.getString(R.string.login_or_pass_incorrect))
            else
                setErrorMessage(it)
        })
    }

    private fun isLogged() {
        authViewModel.authResponse.observe(this, {
            if (it.data.idUser != null) {
                sessionManager?.saveUserId(it.data.idUser)
                sessionManager?.saveUserLogin(it.data.login)
                //sessionManager.saveUserMatricule(it.data.matricule)
                sessionManager?.saveUserFirstName(it.data.firstName)
                sessionManager?.saveUserLastName(it.data.lastName)
                authViewModel.findCarById(CAR_ID)
            }
        })
    }

    private fun isCarExist(){
        authViewModel.car.observe(this, {
            if (it != null) {
                sessionManager?.saveCarId(it.id)
                sessionManager?.saveCarImmatriculation(it.immatriculation)
                startMainActivity()
            }
        })
    }

    private fun startMainActivity() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun setErrorMessage(error: String?) {
        if (error != null) {
            if (errorTv?.visibility == View.GONE)
                errorTv?.visibility = View.VISIBLE
            errorTv?.text = error
        } else {
            if (errorTv?.visibility == View.VISIBLE)
                errorTv?.visibility = View.GONE
        }
    }

    private fun showProgress(test: Boolean) {
        if (test) {
            authProgressLayout?.visibility = View.VISIBLE
            authAccessLayout?.visibility = View.GONE
        } else {
            authProgressLayout?.visibility = View.GONE
            authAccessLayout?.visibility = View.VISIBLE
        }
    }

}