package ru.sicampus.bootcamp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.sicampus.bootcamp.databinding.CenterCardItemBinding
import ru.sicampus.bootcamp.domain.list.CentersEntity
import ru.sicampus.bootcamp.domain.list.UserEntity

class CentersAdapter : ListAdapter<CentersEntity, CentersAdapter.ViewHolder>(CentersDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CenterCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: CenterCardItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CentersEntity) {
            binding.title.text = item.name
            binding.discription.text = item.address
        }

    }

    object CentersDiff : DiffUtil.ItemCallback<CentersEntity>() {
        override fun areItemsTheSame(oldItem: CentersEntity, newItem: CentersEntity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CentersEntity, newItem: CentersEntity): Boolean {
            return oldItem == newItem
        }
    }
}
