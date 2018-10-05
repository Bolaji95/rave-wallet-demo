package com.bolaandroid.ravewalletdemo.view

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bolaandroid.ravewalletdemo.databinding.ActivityMainBinding
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.EditText
import android.view.View
import android.widget.Toast
import com.bolaandroid.ravewalletdemo.viewmodels.MainActivityViewModel
import com.bolaandroid.ravewalletdemo.R
import com.bolaandroid.ravewalletdemo.Utils.Utils
import com.bolaandroid.ravewalletdemo.Utils.Utils.Companion.isvalid
import com.flutterwave.raveandroid.RaveConstants
import com.flutterwave.raveandroid.RavePayActivity
//import com.flutterwave.raveandroid.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    val TAG = "MainActivity"
    var viewModel : MainActivityViewModel = MainActivityViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewmodel = viewModel
        viewModel.onCreate()
        if (com.bolaandroid.ravewalletdemo.Utils.Utils.isUserFirstTimer()){
            showWelcomeRegistrationDialog()
        }

        fundBTN.setOnClickListener(this)
    }



    override fun onClick(p0: View?) {
        when(p0){
            fundBTN -> showPaymentDetailsDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RaveConstants.RAVE_REQUEST_CODE && data!=null){
            var message:String?=data.getStringExtra("response")
            try {
                Log.i(TAG,message)
            }catch (e:NullPointerException){
                Log.i(TAG,"message is null")
            }
            if(resultCode==RavePayActivity.RESULT_SUCCESS){
                Toast.makeText(this,"SUCCESSFUL",Toast.LENGTH_LONG).show()
                viewModel.updateBalance()
                //Probably also save in DB for future reference, but beyond scope of demo
            }else if(resultCode==RavePayActivity.RESULT_ERROR){
                Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show()
            }else if(resultCode==RavePayActivity.RESULT_CANCELED){
                Toast.makeText(this,"CANCELLED",Toast.LENGTH_LONG).show()
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun showWelcomeRegistrationDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.name_requesting_dialog, null)
        dialogBuilder.setView(dialogView)
        val firstNameET = dialogView.findViewById(R.id.firstNameET) as EditText
        val lastNameET = dialogView.findViewById(R.id.lastNameET) as EditText
        val emailET = dialogView.findViewById(R.id.emailET) as EditText
        val alertDialog = dialogBuilder.create()
        alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(isvalid(firstNameET) and isvalid(lastNameET) and isvalid(emailET)){
                    viewModel.setUser(Utils.getString(firstNameET),Utils.getString(lastNameET),Utils.getString(emailET),"0.00")
                    return
                }

            }
        })
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"Cancel",object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                finish()

            }
        })
        alertDialog.show()
    }

    fun showPaymentDetailsDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.funding_details_dialog, null)
        dialogBuilder.setView(dialogView)
        val amountET = dialogView.findViewById(R.id.amount) as EditText
        val referenceET = dialogView.findViewById(R.id.reference) as EditText
        val alertDialog = dialogBuilder.create()
        alertDialog.setButton(Dialog.BUTTON_POSITIVE,"PROCEED",object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(isvalid(amountET) and isvalid(referenceET)){
                    viewModel.initializeRave(this@MainActivity,Utils.getString(amountET),Utils.getString(referenceET))
                    return
                }

            }
        })
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"Cancel",object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                alertDialog.dismiss()

            }
        })
        alertDialog.show()
    }




}
