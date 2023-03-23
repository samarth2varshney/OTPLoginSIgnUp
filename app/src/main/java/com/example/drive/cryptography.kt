package com.example.drive

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class cryptography : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cryptography)

        findViewById<Button>(R.id.encryt).setOnClickListener {
            val s = findViewById<EditText>(R.id.convertText).text.toString()
            val ini = "11111111"

            var cu = 0

            val arr = IntArray(11111111)

            for (i in 0 until s.length) {
                arr[i] = s[i].code
                cu++
            }
            var res = ""
            val bin = IntArray(111)
            var idx = 0

            for (i1 in 0 until cu) {
                var temp = arr[i1]
                for (j in 0 until cu) bin[j] = 0
                idx = 0
                while (temp > 0) {
                    bin[idx++] = temp % 2
                    temp = temp / 2
                }
                var dig = ""
                var temps: String

                for (j in 0..6) {

                    temps = Integer.toString(bin[j])

                    dig = dig + temps
                }
                var revs = ""

                for (j in dig.length - 1 downTo 0) {
                    val ca = dig[j]
                    revs = revs + ca.toString()
                }
                res = res + revs
            }
            var comp:String = ini+res
            var a:String = ""
            var i:Int = 0
            var n = comp.length
            while(i<n){
                if(comp[i]=='1'){
                    var count: Int = 0
                    while(i<n&&comp[i]=='1'&&count<9){
                        count++
                        i++
                    }
                    a =a + count.toString()
                    a= a + "1"
                }
                else if(comp[i]=='0'){
                    var count: Int = 0
                    while(i<n&&comp[i]=='0'&&count<9){
                        count++
                        i=i+1
                    }
                    a =a + count.toString()
                    a= a + "0"
                }
                else
                    i = i + 1
            }
            findViewById<TextView>(R.id.convertedtext).text = a
        }

        findViewById<Button>(R.id.decrypt).setOnClickListener {
            var s = findViewById<EditText>(R.id.convertText).text.toString()
            var i=0
            var x:String =""
            var n=s.length
            while(i<n){
                if(i%2==0){
                    var len:Int = s[i].code-48
                    var z:Int = 0
                    while(z<len){
                        x= x+s[i+1]
                        z++
                    }
                }
                i++
            }
            s=x
            val invalid = "Invalid Code"

            val ini = "11111111"
            var flag = true

            for (i in 0..7) {
                if (ini[i] != s[i]) {
                    flag = false
                    break
                }
            }
            var `val` = ""

            for (i in 8 until s.length) {
                val ch = s[i]
                `val` = `val` + ch.toString()
            }

            val arr = Array(11101) { IntArray(8) }
            var ind1 = -1
            var ind2 = 0

            for (i in 0 until `val`.length) {

                if (i % 7 == 0) {

                    ind1++
                    ind2 = 0
                    val ch = `val`[i]
                    arr[ind1][ind2] = ch.code - '0'.code
                    ind2++
                } else {

                    val ch = `val`[i]
                    arr[ind1][ind2] = ch.code - '0'.code
                    ind2++
                }
            }
            val num = IntArray(11111)
            var nind = 0
            var tem = 0
            var cu = 0

            for (i in 0..ind1) {
                cu = 0
                tem = 0

                for (j in 6 downTo 0) {
                    val tem1 = Math.pow(2.0, cu.toDouble()).toInt()
                    tem += arr[i][j] * tem1
                    cu++
                }
                num[nind++] = tem
            }
            var ret = ""
            var ch: Char

            for (i in 0 until nind) {
                ch = num[i].toChar()
                ret = ret + ch.toString()
            }
             if (`val`.length % 7 == 0 && flag == true) {
                 findViewById<TextView>(R.id.convertedtext).text = ret
            } else {
                 findViewById<TextView>(R.id.convertedtext).text = invalid
            }
        }
    }
}