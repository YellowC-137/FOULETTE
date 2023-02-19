package com.example.foulette.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foulette.databinding.ItemBottomImgBinding
import com.example.foulette.databinding.ItemHistoryBinding
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.ui.history.HistoryAdapter

class BottomSheetPhotoAdapter :
    ListAdapter<String, BottomSheetPhotoAdapter.ViewHolder>(diffUtil) {
    class ViewHolder(private val binding: ItemBottomImgBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = with(binding) {
            Glide.with(ivImg.context).load(item).into(ivImg)
            executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return BottomSheetPhotoAdapter.ViewHolder(
            ItemBottomImgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ) = oldItem.hashCode() == newItem.hashCode()
        }
    }
}