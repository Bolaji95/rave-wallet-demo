package com.bolaandroid.ravewalletdemo

import android.app.Activity
import android.databinding.ObservableField
import com.bolaandroid.ravewalletdemo.Utils.Utils
import com.flutterwave.raveandroid.RavePayManager
import okhttp3.internal.Util
import java.text.DecimalFormat

/**
 * Created by Owner on 10/4/2018.
 */

class MainActivityViewModel : ViewModel{
    var welcomeText = ObservableField<String>()
    var totalTransaction = ObservableField<String>()
    var user : User? = null
    var lastAmountPaid: Double = 0.00

    fun setUser(firstName:String,lastName:String,email:String,totalAmountDeposited:String){
        val initialDeposit : Double = 0.00
        user = User(firstName,initialDeposit)
        welcomeText.set("Welcome, $firstName")
        setAmount(initialDeposit)
        Utils.saveUserNameToPrefs(firstName = firstName,lastName = lastName,email = email,totalAmountDeposited=totalAmountDeposited)
    }

    fun setUser(firstName:String,totalDeposited:Double){
        user = User(firstName,totalDeposited)
        welcomeText.set("Welcome, $firstName")
        setAmount(totalDeposited)
    }

    fun setAmount(value:Double){
        totalTransaction.set("₦"+formatAmount(value))
    }

    fun formatAmount(value:Double): String{
        var format:DecimalFormat = DecimalFormat("#,###,###")
        return format.format(value)
    }

    fun initializeRave(activity: Activity,amount:String,reference:String){
        var ravePayManager : RavePayManager = RavePayManager(activity).setAmount(amount.toDouble())
                .setCountry("NG")
                .setCurrency("NGN")
                .setEmail(com.bolaandroid.ravewalletdemo.Utils.Utils.getEmail())
                .setfName(com.bolaandroid.ravewalletdemo.Utils.Utils.getFirstName())
                .setlName(com.bolaandroid.ravewalletdemo.Utils.Utils.getLastName())
                .setTxRef(reference)
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .setPublicKey("FLWPUBK-2e6c353b8be01c86a8c5c20d58e4ef3f-X")
                .setSecretKey("FLWSECK-00588e40422a7fccef652dae1b5ac4fd-X") //not allowed but this is just a demo
                .onStagingEnv(true)
                .allowSaveCardFeature(true)
        lastAmountPaid = amount.toDouble()
        ravePayManager.initialize()
    }

    // a workaround has I am not able to get a response from rave library
    fun updateBalance(){
        var totalDeposited : Double = Utils.getTotalDeposit().toDouble()
        totalDeposited=totalDeposited+lastAmountPaid
        Utils.saveUserTotalDeposites(totalAmountDeposited = totalDeposited.toString())
        setAmount(totalDeposited)
    }

    override fun onCreate() {
        if(!Utils.isUserFirstTimer()){
            setUser(Utils.getFirstName(),Utils.getTotalDeposit().toDouble())
        }
    }

    override fun onPause() {

    }

    override fun onResume() {

    }

    override fun onDestroy() {

    }



}