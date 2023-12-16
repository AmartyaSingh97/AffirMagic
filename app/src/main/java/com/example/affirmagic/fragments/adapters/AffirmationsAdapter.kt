package com.example.affirmagic.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.affirmagic.databinding.ItemAffirmationsLayoutBinding
import com.example.affirmagic.db.entity.AffirmationsEntity

class AffirmationsAdapter() : RecyclerView.Adapter<AffirmationsAdapter.ViewHolder>() {

    private var affirmations = listOf<AffirmationsEntity>()

    inner class ViewHolder(private val binding: ItemAffirmationsLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: AffirmationsEntity)= binding.apply {
            title.text = item.themeTitle
            Glide.with(itemView)
                .load(item.bgImageUrl).centerCrop().
                into(image)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    )   = ViewHolder(ItemAffirmationsLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
    ))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(affirmations[position])
    }

    override fun getItemCount(): Int {
        return affirmations.size
    }

    fun setAffirmationItems(items:List<AffirmationsEntity>){
        affirmations = items
    }

}