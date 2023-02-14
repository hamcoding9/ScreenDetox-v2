package com.hamcoding.screendetox.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamcoding.screendetox.data.App
import com.hamcoding.screendetox.data.RequestInfo
import com.hamcoding.screendetox.databinding.ItemNotificationBinding
import com.hamcoding.screendetox.databinding.ItemStatsBinding
import com.hamcoding.screendetox.ui.stats.AppAdapter.Companion.diffUtil

class NotificationAdapter(private val onSubmitClick: (RequestInfo) -> Unit) :
    ListAdapter<RequestInfo, NotificationAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: RequestInfo) {
            binding.tvSenderEmail.text = data.senderEmail
            binding.tvRequestedDate.text = data.requestedDate
            binding.BtnSubmit.setOnClickListener {
                onSubmitClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemNotificationBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RequestInfo>() {
            override fun areItemsTheSame(oldItem: RequestInfo, newItem: RequestInfo): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: RequestInfo, newItem: RequestInfo): Boolean {
                return oldItem == newItem
            }

        }
    }

}