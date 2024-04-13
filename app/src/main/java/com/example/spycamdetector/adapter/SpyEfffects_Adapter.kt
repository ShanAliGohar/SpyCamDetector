package com.example.spycamdetector.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.spycamdetector.R
import com.example.spycamdetector.adapter.SpyEfffects_Adapter.ItemViewHolder
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.filters.BlackAndWhiteFilter
import com.otaliastudios.cameraview.filters.InvertColorsFilter
import com.otaliastudios.cameraview.filters.LomoishFilter
import com.otaliastudios.cameraview.filters.SepiaFilter
import com.otaliastudios.cameraview.filters.VignetteFilter

class SpyEfffects_Adapter(
    var name: ArrayList<String>,
    var imageArray: ArrayList<Int>,
    var context: Context,
    var cameraView: CameraView
) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.spyeffects_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.txteffect.text = name[position]
        holder.iv_effects.setImageResource(imageArray[position])
        holder.crdeffect.setOnClickListener {
            if (position == 0) {
                val blackAndWhiteFilter = BlackAndWhiteFilter()
                cameraView.filter = blackAndWhiteFilter
            }
            if (position == 1) {
                val sepiaFilter = SepiaFilter()
                cameraView.filter = sepiaFilter
            }
            if (position == 2) {
                val invertColorsFilter = InvertColorsFilter()
                cameraView.filter = invertColorsFilter
            }
            if (position == 3) {
                val lomoishFilter = LomoishFilter()
                cameraView.filter = lomoishFilter
            }
            if (position == 4) {
                val vignetteFilter = VignetteFilter()
                cameraView.filter = vignetteFilter
            }
        }
    }

    override fun getItemCount(): Int {
        return name.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txteffect: TextView
        var iv_effects: ImageView
        var crdeffect: CardView

        init {
            txteffect = itemView.findViewById(R.id.tveffects)
            crdeffect = itemView.findViewById(R.id.crdeffects)
            iv_effects = itemView.findViewById(R.id.iv_effects)
        }
    }
}
