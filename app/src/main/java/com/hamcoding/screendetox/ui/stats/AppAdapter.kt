package com.hamcoding.screendetox.ui.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamcoding.screendetox.data.App
import com.hamcoding.screendetox.databinding.ItemStatsBinding

class AppAdapter() : ListAdapter<App, AppAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(private val binding: ItemStatsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(app: App) {
            binding.tvAppName.text = app.appName
            binding.tvAppUsage.text = app.usageDuration
            binding.progressBar.progress = app.usagePercentage
            binding.ivAppIcon.setImageDrawable(app.appIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemStatsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<App>() {
            override fun areItemsTheSame(oldItem: App, newItem: App): Boolean {
                return oldItem.appName == newItem.appName
            }

            override fun areContentsTheSame(oldItem: App, newItem: App): Boolean {
                return oldItem == newItem
            }
        }
    }

}