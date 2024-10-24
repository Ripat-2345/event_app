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
import com.example.dicodingeventapp.data.local.entity.FavoriteEventsEntity
import com.example.dicodingeventapp.data.local.entity.FinishedEventsEntity
import com.example.dicodingeventapp.data.local.entity.UpcomingEventsEntity
import com.example.dicodingeventapp.data.remote.response.DetailEventItem
import com.example.dicodingeventapp.databinding.ActivityDetailEventBinding
import com.example.dicodingeventapp.ui.ViewModelFactory
import com.example.dicodingeventapp.ui.favorite.FavoriteViewModel
import com.example.dicodingeventapp.ui.finished.FinishedViewModel
import com.example.dicodingeventapp.ui.upcoming.UpcomingViewModel

class DetailEventActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var detailEventActivityDetailEventBinding: ActivityDetailEventBinding
    private val detailEventViewModel: DetailEventViewModel by viewModels<DetailEventViewModel>()
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val upcomingViewModel: UpcomingViewModel by viewModels<UpcomingViewModel> { factory }
    private val finishedViewModel: FinishedViewModel by viewModels<FinishedViewModel> { factory }
    private val favoriteViewModel: FavoriteViewModel by viewModels<FavoriteViewModel> { factory }

    private lateinit var upcomingEventsEntity: UpcomingEventsEntity
    private lateinit var finishedEventsEntity: FinishedEventsEntity

    companion object{
        const val IDEVENT = "id_event"
        const val STATUSEVENT = "status_event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        detailEventActivityDetailEventBinding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(detailEventActivityDetailEventBinding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)


        val idEvent = intent.getStringExtra(IDEVENT)
        val statusEvent = intent.getStringExtra(STATUSEVENT)

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

        // favorite feature section
        val icFavorite = detailEventActivityDetailEventBinding.btnFavorite
        if(statusEvent.toBoolean()){
            upcomingViewModel.checkIsFavorite(idEvent.toInt()).observe(this){ data ->
                upcomingEventsEntity = data
                if (data.isFavorite == true) {
                    icFavorite.setIconResource(R.drawable.baseline_favorite_24)
                } else {
                    icFavorite.setIconResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }else{
            finishedViewModel.checkIsFavorite(idEvent.toInt()).observe(this){ data ->
                finishedEventsEntity = data
                if (data.isFavorite == true) {
                    icFavorite.setIconResource(R.drawable.baseline_favorite_24)
                } else {
                    icFavorite.setIconResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }

        // onclick button add to favorite
        detailEventActivityDetailEventBinding.btnFavorite.setOnClickListener(this)

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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_favorite -> {
                val idEvent = intent.getStringExtra(IDEVENT)
                val statusEvent = intent.getStringExtra(STATUSEVENT)
                // tap button favorite save to local storage room
                if(idEvent != null){
                    if(statusEvent.toBoolean()){
                        if (upcomingEventsEntity.isFavorite == true){
                            upcomingViewModel.deleteEvent(idEvent.toInt())
                            favoriteViewModel.deleteFavoriteEvent(idEvent.toInt())
                        }else{
                            upcomingViewModel.saveEvent(idEvent.toInt())
                            favoriteViewModel.insertFavoriteEvent(FavoriteEventsEntity(
                                upcomingEventsEntity.id,
                                upcomingEventsEntity.summary,
                                upcomingEventsEntity.mediaCover,
                                upcomingEventsEntity.registrants,
                                upcomingEventsEntity.imageLogo,
                                upcomingEventsEntity.link,
                                upcomingEventsEntity.description,
                                upcomingEventsEntity.ownerName,
                                upcomingEventsEntity.cityName,
                                upcomingEventsEntity.quota,
                                upcomingEventsEntity.name,
                                upcomingEventsEntity.beginTime,
                                upcomingEventsEntity.endTime,
                                upcomingEventsEntity.category,
                                statusEvent = true
                            ))
//                            Toast.makeText(this, "Berhasil menambahkan ke favorite!", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        if (finishedEventsEntity.isFavorite == true){
                            finishedViewModel.deleteEvent(idEvent.toInt())
                            favoriteViewModel.deleteFavoriteEvent(idEvent.toInt())
                        }else{
                            finishedViewModel.saveEvent(idEvent.toInt())
                            favoriteViewModel.insertFavoriteEvent(FavoriteEventsEntity(
                                finishedEventsEntity.id,
                                finishedEventsEntity.summary,
                                finishedEventsEntity.mediaCover,
                                finishedEventsEntity.registrants,
                                finishedEventsEntity.imageLogo,
                                finishedEventsEntity.link,
                                finishedEventsEntity.description,
                                finishedEventsEntity.ownerName,
                                finishedEventsEntity.cityName,
                                finishedEventsEntity.quota,
                                finishedEventsEntity.name,
                                finishedEventsEntity.beginTime,
                                finishedEventsEntity.endTime,
                                finishedEventsEntity.category,
                                statusEvent = false
                            ))
//                            Toast.makeText(this, "Berhasil menambahkan ke favorite!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}