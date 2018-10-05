package com.bolaandroid.ravewalletdemo.Utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import com.bolaandroid.ravewalletdemo.Constants
import com.bolaandroid.ravewalletdemo.Context.App
import java.util.*

/**
 * Created by Owner on 10/4/2018.
 */
class Utils{
    companion object {
        fun saveUserNameToPrefs(firstName:String,lastName:String,email:String,totalAmountDeposited:String){
            var context : Context = App.context()
            var sharedPreferences : SharedPreferences = context.getSharedPreferences(Constants.userInfo().USER_INFO,Context.MODE_PRIVATE)
            var editor:android.content.SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(Constants.userInfo().USER_INFO_FIRST_NAME,firstName)
            editor.putString(Constants.userInfo().USER_INFO_LAST_NAME,lastName)
            editor.putString(Constants.userInfo().USER_INFO_EMAIL,email)
            editor.putString(Constants.userInfo().USER_TOTAL_DEPOSITED,totalAmountDeposited)
            editor.apply()
        }

        fun setUserFirstTimer(firstTime:Boolean){
            var context : Context = App.context()
            var sharedPreferences : SharedPreferences = context.getSharedPreferences(Constants.userInfo().USER_INFO,Context.MODE_PRIVATE)
            var editor:android.content.SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(Constants.userInfo().FIRST_TIMER,firstTime)
            editor.apply()
        }

        fun isUserFirstTimer():Boolean{
            var context : Context = App.context()
            var sharedPreferences : SharedPreferences = context.getSharedPreferences(Constants.userInfo().USER_INFO,Context.MODE_PRIVATE)
            // return sharedPreferences.getBoolean(Constants.userInfo().FIRST_TIMER,false)
            if(sharedPreferences.contains(Constants.userInfo().USER_INFO_FIRST_NAME)){
                return false
            }
            return true
        }

        fun getFirstName():String{
            var context : Context = App.context()
            var sharedPreferences : SharedPreferences = context.getSharedPreferences(Constants.userInfo().USER_INFO,Context.MODE_PRIVATE)
            if(sharedPreferences.contains(Constants.userInfo().USER_INFO_FIRST_NAME)){
                return sharedPreferences.getString(Constants.userInfo().USER_INFO_FIRST_NAME,"")
            }
            return ""
        }

        fun getLastName():String{
            var context : Context = App.context()
            var sharedPreferences : SharedPreferences = context.getSharedPreferences(Constants.userInfo().USER_INFO,Context.MODE_PRIVATE)
            if(sharedPreferences.contains(Constants.userInfo().USER_INFO_LAST_NAME)){
                return sharedPreferences.getString(Constants.userInfo().USER_INFO_LAST_NAME,"")
            }
            return ""
        }

        fun getEmail():String{
            var context : Context = App.context()
            var sharedPreferences : SharedPreferences = context.getSharedPreferences(Constants.userInfo().USER_INFO,Context.MODE_PRIVATE)
            if(sharedPreferences.contains(Constants.userInfo().USER_INFO_EMAIL)){
                return sharedPreferences.getString(Constants.userInfo().USER_INFO_EMAIL,"")
            }
            return ""
        }

        fun getTotalDeposit():String{
            var context : Context = App.context()
            var sharedPreferences : SharedPreferences = context.getSharedPreferences(Constants.userInfo().USER_INFO,Context.MODE_PRIVATE)
            if(sharedPreferences.contains(Constants.userInfo().USER_TOTAL_DEPOSITED)){
                return sharedPreferences.getString(Constants.userInfo().USER_TOTAL_DEPOSITED,"")
            }
            return ""
        }


        fun saveUserTotalDeposites(totalAmountDeposited:String){
            var context : Context = App.context()
            var sharedPreferences : SharedPreferences = context.getSharedPreferences(Constants.userInfo().USER_INFO,Context.MODE_PRIVATE)
            var editor:android.content.SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(Constants.userInfo().USER_TOTAL_DEPOSITED,totalAmountDeposited)
            editor.apply()
        }


        fun isvalid(editText: EditText):Boolean{
            val content : String = editText.text.toString().trim()
            if(content.equals("")){
                editText.setError("Field can't be blank")
                return false
            }
            return true
        }

        fun getString(editText: EditText):String{
            val content : String = editText.text.toString().trim()
            return content
        }


    }


}