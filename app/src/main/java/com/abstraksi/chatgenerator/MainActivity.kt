package com.abstraksi.chatgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var value = String()
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        MobileAds.initialize(this){}
        val testDeviceId = ArrayList<String>()
        testDeviceId.add(AdRequest.DEVICE_ID_EMULATOR)
        testDeviceId.add("C10581A3B0647778855C8312F11AE126")

        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-3559020312196470/9825792744"
        mAdView = findViewById(R.id.adView)

        val requestConfig = RequestConfiguration.Builder()
            .setTestDeviceIds(testDeviceId).build()
        MobileAds.setRequestConfiguration(requestConfig)

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val toolbar = toolbar
        val drawer = drawer_layout
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        val navigationView = navigation_view

        drawer.setDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_about_us -> {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("Tentang kami")
                    dialog.setMessage("we are a group of people who like to make mobile apps.")
                    dialog.setNegativeButton("OK", null)
                    dialog.show()
                }
                R.id.nav_more_apps -> {
                    Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        cv_btn_convert.setOnClickListener{
            val et = et_input.text.toString()
            convertText(et)
            et_output_result.text = value
        }

        iv_btn_copy.setOnClickListener {

            if(et_output_result.text.isEmpty()){
                Toast.makeText(this,"masUkIn duLu SaYang kAta2nYa", Toast.LENGTH_SHORT).show()
            }
            else{
                val clipboard= getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("content", value)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "sUdaH dIsAliN", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertText(et: String) {

        var text = ""
        val rand = Random()
        var char = et.toCharArray()

        for(i in et.indices){
            val randNum = rand.nextInt(3) + 1
            if(i%randNum == 0){
                char[i] = Character.toUpperCase(et[i])
            }
            else {
                char[i] = Character.toLowerCase(et[i])
            }
        }
        text = String(char)
        value = text
    }
}
