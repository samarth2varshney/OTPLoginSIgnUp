package com.example.drive

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.common.api.Status
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes

class SmsBroadcastReceiver : BroadcastReceiver(){

    var smsBroadcastReciverListner : SmsBroadcastRecieverListner ?=null

    override fun onReceive(context: Context?, intent: Intent?) {

        if(SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action){

            val extras = intent.extras
            val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

            when(smsRetrieverStatus.statusCode){

                CommonStatusCodes.SUCCESS->{
                    val messageIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    smsBroadcastReciverListner?.onSuccess(messageIntent)
                }

                CommonStatusCodes.TIMEOUT->{
                    smsBroadcastReciverListner?.onFaliure()
                }

            }

        }

    }

    interface SmsBroadcastRecieverListner{
        fun onSuccess(intent: Intent?)
        fun onFaliure()
    }

}
