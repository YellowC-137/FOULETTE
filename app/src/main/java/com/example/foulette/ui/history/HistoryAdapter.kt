package com.example.foulette.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foulette.R
import com.example.foulette.databinding.ItemHistoryBinding
import com.example.foulette.domain.models.HistoryResult

class HistoryAdapter(
    private val deleteClicked: (HistoryResult) -> Unit,
    private val itemClicked: (HistoryResult) -> Unit
) : PagingDataAdapter<HistoryResult, HistoryAdapter.ViewHolder>(diffutil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bindItems(it, deleteClicked, itemClicked)
        }
    }

    class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(
            item: HistoryResult,
            deleteClicked: (HistoryResult) -> Unit,
            itemClicked: (HistoryResult) -> Unit
        ) = with(binding) {
            history = item
            Glide.with(ivRestaurant.context).load(item.restaurantImgUrl).error(R.drawable.no_img).into(ivRestaurant)

            ivDelete.setOnClickListener {
                deleteClicked.invoke(item)
            }

            historyContainer.setOnClickListener {
                itemClicked.invoke(item)
            }

            executePendingBindings()
        }

    }

    companion object {
        private val diffutil = object : DiffUtil.ItemCallback<HistoryResult>() {
            override fun areItemsTheSame(
                oldItem: HistoryResult,
                newItem: HistoryResult
            ) = oldItem.restaurantName == newItem.restaurantName

            override fun areContentsTheSame(
                oldItem: HistoryResult,
                newItem: HistoryResult
            ) = oldItem == newItem
        }
    }
}
