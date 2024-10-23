package com.example.dicodingeventapp.ui.detail_event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.data.remote.response.DetailEventItem
import com.example.dicodingeventapp.databinding.ActivityDetailEventBinding

class DetailEventActivity : AppCompatActivity() {
    private lateinit var detailEventActivityDetailEventBinding: ActivityDetailEventBinding
    private val detailEventViewModel: DetailEventViewModel by viewModels<DetailEventViewModel>()

    companion object{
        const val IDEVENT = "id_event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        detailEventActivityDetailEventBinding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(detailEventActivityDetailEventBinding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)

        val idEvent = intent.getStringExtra(IDEVENT)
        println("DEBUG ID EVENT: $idEvent")
        detailEventViewModel.getDetailEvent(idEvent!!)
        detailEventViewModel.detailEvent.observe(this){ dataEvent ->
            setDetailEventData(dataEvent.event)
        }

        detailEventViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailEventViewModel.toastText.observe(this){ toastText ->
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }

        detailEventActivityDetailEventBinding.btnFavorite.setOnClickListener{
            // tap button favorite save to local storage room
            println("Hello berhasil print!")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setDetailEventData(dataEvent: DetailEventItem){
        supportActionBar?.title = dataEvent.name
        detailEventActivityDetailEventBinding.nameDetailEvent.text = dataEvent.name
        detailEventActivityDetailEventBinding.ownerNameDetailEvent.text = dataEvent.ownerName
        detailEventActivityDetailEventBinding.beginTimeDetailEvent.text = "Waktu Mulai: " + dataEvent.beginTime
        detailEventActivityDetailEventBinding.quotaDetailEvent.text = "Kuota: " + dataEvent.quota.toString()
        detailEventActivityDetailEventBinding.registrantDetailEvent.text = "Sisa Kuota: " + (dataEvent.quota - dataEvent.registrants).toString()
        detailEventActivityDetailEventBinding.descriptionDetailEvent.text = HtmlCompat.fromHtml(
            dataEvent.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        Glide.with(this).load(dataEvent.mediaCover).into(detailEventActivityDetailEventBinding.imgDetailEvent)

        detailEventActivityDetailEventBinding.registerDetailEvent.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(dataEvent.link)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean){
        detailEventActivityDetailEventBinding.registerDetailEvent.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        detailEventActivityDetailEventBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}