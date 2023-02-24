package com.hamcoding.screendetox.ui.rank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamcoding.screendetox.data.db.entity.User
import com.hamcoding.screendetox.databinding.ItemRankBinding

class RankAdapter() : ListAdapter<User, RankAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(private val binding: ItemRankBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, position: Int) {
            val rank = position + 1
            binding.tvUserRank.text = rank.toString()
            binding.user = user
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemRankBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.email == newItem.email
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}