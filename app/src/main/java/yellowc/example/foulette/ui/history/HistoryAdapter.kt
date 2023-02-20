package yellowc.example.foulette.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import yellowc.example.foulette.databinding.ItemHistoryBinding
import yellowc.example.foulette.domain.models.HistoryResult

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
            ivRestaurant.setImageBitmap(item.restaurantImg)
            val price = listOf("무료", "저렴함", "보통", "조금 비쌈", "매우 비쌈")
            tvPrice.text = "가격대 : ${price[item.price]}"
            rating.rating = item.rate.toFloat()

            ivDelete.setOnClickListener {
                deleteClicked.invoke(item)
            }

            ivRoute.setOnClickListener {
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
