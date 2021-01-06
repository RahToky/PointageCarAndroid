package mg.pulse.pointagecar.models.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SessionManager(var context:Context) {
    private val PREFERENCE_KEY = "p_session"
    private val USER_ID = "id"
    private val USER_LOGIN = "login"
    private val USER_MATRICULE = "matricule"
    private val USER_FNAME = "firstName"
    private val USER_LNAME = "lastName"
    private var sharedPreferences:SharedPreferences = context.getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE)

    fun saveUserId(id:String){
        var editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_ID,id)
        editor.commit()
    }

    fun saveUserLogin(login:String){
        var editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_LOGIN,login)
        editor.commit()
    }

    fun saveUserFirstName(firstName:String){
        var editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_FNAME,firstName)
        editor.commit()
    }

    fun saveUserLastName(lastName:String){
        var editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_LNAME,lastName)
        editor.commit()
    }

    fun saveUserMatricule(matricule:String){
        var editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_MATRICULE,matricule)
        editor.commit()
    }

    fun getUserId():String? = sharedPreferences.getString(USER_ID,null)
    fun getUserLogin():String? = sharedPreferences.getString(USER_LOGIN,null)
    fun getUserFirstName():String? = sharedPreferences.getString(USER_FNAME,null)
    fun getUserLastName():String? = sharedPreferences.getString(USER_LNAME,null)
    fun getUserMatricule():String? = sharedPreferences.getString(USER_MATRICULE,null)

    fun clear(){
        sharedPreferences.edit().clear().commit()
    }
}