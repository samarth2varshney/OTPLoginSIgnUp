package com.example.drive

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Telephony.Sms
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class OPTActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var verifyBtn: Button
    private lateinit var resendTV: TextView
    private lateinit var inputOTP1: EditText
    private lateinit var inputOTP2: EditText
    private lateinit var inputOTP3: EditText
    private lateinit var inputOTP4: EditText
    private lateinit var inputOTP5: EditText
    private lateinit var inputOTP6: EditText
    private lateinit var button1:Button
    private lateinit var button2:Button
    private lateinit var button3:Button
    private lateinit var button4:Button
    private lateinit var button5:Button
    private lateinit var button6:Button
    private lateinit var button7:Button
    private lateinit var button8:Button
    private lateinit var button9:Button
    private lateinit var button0:Button
    private lateinit var progressBar: ProgressBar
    private lateinit var backspace:ImageView

    var smsBroadcastReceiver: SmsBroadcastReceiver? = null
    private val REQ_USER_CONSENT = 200
    private lateinit var OTP: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_optactivity)
        OTP = intent.getStringExtra("OTP").toString()
        resendToken = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!

        init()
        progressBar.visibility = View.INVISIBLE
        inputOTP1.requestFocus()
        addTextChangeListener()
        resendOTPTvVisibility()
        buttonclick()
        startSmartUserConsent()

        resendTV.setOnClickListener {
            resendVerificationCode()
            resendOTPTvVisibility()
        }

        verifyBtn.setOnClickListener {
            val typedOTP =
                (inputOTP1.text.toString() + inputOTP2.text.toString() + inputOTP3.text.toString()
                        + inputOTP4.text.toString() + inputOTP5.text.toString() + inputOTP6.text.toString())

            if (typedOTP.isNotEmpty()) {
                if (typedOTP.length == 6) {
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOTP
                    )
                    progressBar.visibility = View.VISIBLE
                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun startSmartUserConsent() {
        val client = SmsRetriever.getClient(this)
        client.startSmsUserConsent(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQ_USER_CONSENT){

            if(resultCode == RESULT_OK && data!=null){

                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)

                getOTPFromMessage(message)

            }

        }

    }

    private fun getOTPFromMessage(message: String?) {

        val otpPatter = Pattern.compile("(|^)\\d{6}")
        val matcher = otpPatter.matcher(message)
        if(matcher.find()){
            val otp:String = matcher.group(0)
            inputOTP1.setText("${otp[0]}")
            inputOTP2.setText("${otp[1]}")
            inputOTP3.setText("${otp[2]}")
            inputOTP4.setText("${otp[3]}")
            inputOTP5.setText("${otp[4]}")
            inputOTP6.setText("${otp[5]}")

            val typedOTP =
                (inputOTP1.text.toString() + inputOTP2.text.toString() + inputOTP3.text.toString()
                        + inputOTP4.text.toString() + inputOTP5.text.toString() + inputOTP6.text.toString())

            if (typedOTP.isNotEmpty()) {
                if (typedOTP.length == 6) {
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOTP
                    )
                    progressBar.visibility = View.VISIBLE
                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun registerBroadcastReciver(){

        smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver!!.smsBroadcastReciverListner = object : SmsBroadcastReceiver.SmsBroadcastRecieverListner{
            override fun onSuccess(intent: Intent?) {
                startActivityForResult(intent!!,REQ_USER_CONSENT)
            }

            override fun onFaliure() {

            }

        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver,intentFilter)

    }

    override fun onStart() {
        super.onStart()
        registerBroadcastReciver()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(smsBroadcastReceiver)
    }

    private fun eraseText() {
        if (inputOTP6.text.isNotEmpty()){
            inputOTP6.setText("")
            inputOTP6.requestFocus()
        }
        else if (inputOTP5.text.isNotEmpty()){
            inputOTP5.setText("")
            inputOTP5.requestFocus()
        }
        else if (inputOTP4.text.isNotEmpty()) {
            inputOTP4.setText("")
            inputOTP4.requestFocus()
        }
        else if (inputOTP3.text.isNotEmpty()){
            inputOTP3.setText("")
            inputOTP3.requestFocus()
        }
        else if (inputOTP2.text.isNotEmpty()) {
            inputOTP2.setText("")
            inputOTP2.requestFocus()
        }
        else if (inputOTP1.text.isNotEmpty()){
            inputOTP1.setText("")
            inputOTP1.requestFocus()
        }
    }

    fun buttonclick(){
        button1.setOnClickListener {
            setText("1")
        }
        button2.setOnClickListener {
            setText("2")
        }
        button3.setOnClickListener {
            setText("3")
        }
        button4.setOnClickListener {
            setText("4")
        }
        button5.setOnClickListener {
            setText("5")
        }
        button6.setOnClickListener {
            setText("6")
        }
        button7.setOnClickListener {
            setText("7")
        }
        button8.setOnClickListener {
            setText("8")
        }
        button9.setOnClickListener {
            setText("9")
        }
        button0.setOnClickListener {
            setText("0")
        }
        backspace.setOnClickListener {
            eraseText()
        }
    }

    fun setText(text:String){
        if (inputOTP1.text.isEmpty()) inputOTP1.setText(text)
        else if (inputOTP2.text.isEmpty()) inputOTP2.setText(text)
        else if (inputOTP3.text.isEmpty()) inputOTP3.setText(text)
        else if (inputOTP4.text.isEmpty()) inputOTP4.setText(text)
        else if (inputOTP5.text.isEmpty()) inputOTP5.setText(text)
        else if (inputOTP6.text.isEmpty()) inputOTP6.setText(text)
    }

    private fun resendOTPTvVisibility() {
        inputOTP1.setText("")
        inputOTP2.setText("")
        inputOTP3.setText("")
        inputOTP4.setText("")
        inputOTP5.setText("")
        inputOTP6.setText("")
        inputOTP1.requestFocus()
        resendTV.visibility = View.INVISIBLE
        resendTV.isEnabled = false

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            resendTV.visibility = View.VISIBLE
            resendTV.isEnabled = true
        }, 60000)
    }

    private fun resendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(0L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {

            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }
            progressBar.visibility = View.VISIBLE

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            OTP = verificationId
            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Authenticate Successfully", Toast.LENGTH_SHORT).show()
                    sendToMain()
                } else {
                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }
                }
                progressBar.visibility = View.VISIBLE
            }
    }

    private fun sendToMain() {
        startActivity(Intent(this, MainActivity2::class.java))
        finish()
    }

    private fun addTextChangeListener() {
        inputOTP1.addTextChangedListener(EditTextWatcher(inputOTP1))
        inputOTP2.addTextChangedListener(EditTextWatcher(inputOTP2))
        inputOTP3.addTextChangedListener(EditTextWatcher(inputOTP3))
        inputOTP4.addTextChangedListener(EditTextWatcher(inputOTP4))
        inputOTP5.addTextChangedListener(EditTextWatcher(inputOTP5))
        inputOTP6.addTextChangedListener(EditTextWatcher(inputOTP6))
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.otpProgressBar)
        verifyBtn = findViewById(R.id.verifyOTPBtn)
        resendTV = findViewById(R.id.resendTextView)
        inputOTP1 = findViewById(R.id.otpEditText1)
        inputOTP2 = findViewById(R.id.otpEditText2)
        inputOTP3 = findViewById(R.id.otpEditText3)
        inputOTP4 = findViewById(R.id.otpEditText4)
        inputOTP5 = findViewById(R.id.otpEditText5)
        inputOTP6 = findViewById(R.id.otpEditText6)
        button1 = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button8)
        button8 = findViewById(R.id.button9)
        button9 = findViewById(R.id.button10)
        button0 = findViewById(R.id.button11)
        backspace = findViewById(R.id.backspace)
    }

    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {

            val text = p0.toString()
            when (view.id) {
                R.id.otpEditText1 -> if (text.length == 1) inputOTP2.requestFocus()
                R.id.otpEditText2 -> if (text.length == 1) inputOTP3.requestFocus() else if (text.isEmpty()) inputOTP1.requestFocus()
                R.id.otpEditText3 -> if (text.length == 1) inputOTP4.requestFocus() else if (text.isEmpty()) inputOTP2.requestFocus()
                R.id.otpEditText4 -> if (text.length == 1) inputOTP5.requestFocus() else if (text.isEmpty()) inputOTP3.requestFocus()
                R.id.otpEditText5 -> if (text.length == 1) inputOTP6.requestFocus() else if (text.isEmpty()) inputOTP4.requestFocus()
                R.id.otpEditText6 -> if (text.isEmpty()) inputOTP5.requestFocus()
            }

        }
    }


}