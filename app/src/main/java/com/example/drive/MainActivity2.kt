package com.example.drive

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.drive.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import me.relex.circleindicator.CircleIndicator

class MainActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var signOutBtn: Button
    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator
    private lateinit var binding: ActivityMain2Binding
    val db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var arrayList = arrayListOf("olQrehXegro","Q9WMfd96qVo","c1ICgOqhZq4","rGUv7hMqo_w","byJgRDFdUj0")

        val Movies = db.document("/CBSE/CBSE")
        Movies.get()
            .addOnSuccessListener { document ->
                if (document != null && document.data!=null){
                    //arrayList = document.data as ArrayList<String>
                }
            }

        Glide.with(this)
            .load("https://img.youtube.com/vi/${arrayList[0]}/hqdefault.jpg")
            .into(binding.noidaButton)

        Glide.with(this)
            .load("https://img.youtube.com/vi/${arrayList[1]}/hqdefault.jpg")
            .into(binding.ghaziabadButton)

        Glide.with(this)
            .load("https://img.youtube.com/vi/${arrayList[2]}/hqdefault.jpg")
            .into(binding.newDelhiButton)

        Glide.with(this)
            .load("https://img.youtube.com/vi/${arrayList[3]}/hqdefault.jpg")
            .into(binding.gurgaonButton)

        viewPagerAdapter = ImageSlideAdapter(this,arrayList)
        findViewById<ViewPager>(R.id.viewpager).adapter = viewPagerAdapter
        indicator = findViewById(R.id.indicator) as CircleIndicator
        indicator.setViewPager(findViewById(R.id.viewpager))

        binding.noidaButton.setOnClickListener {
            val intent2 = Intent(this, CustomUiActivity::class.java)
            intent2.putExtra("youtubelink",arrayList[0])
            startActivity(intent2)
        }

        binding.ghaziabadButton.setOnClickListener {
            val intent2 = Intent(this, CustomUiActivity::class.java)
            intent2.putExtra("youtubelink",arrayList[1])
            startActivity(intent2)
        }

        binding.newDelhiButton.setOnClickListener {
            val intent2 = Intent(this, CustomUiActivity::class.java)
            intent2.putExtra("youtubelink",arrayList[2])
            startActivity(intent2)
        }

        binding.gurgaonButton.setOnClickListener {
            val intent2 = Intent(this, CustomUiActivity::class.java)
            intent2.putExtra("youtubelink",arrayList[3])
            startActivity(intent2)
        }

        auth = FirebaseAuth.getInstance()
        signOutBtn = findViewById(R.id.signOutBtn)

        signOutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, PhoneActivity::class.java))
        }

    }
}