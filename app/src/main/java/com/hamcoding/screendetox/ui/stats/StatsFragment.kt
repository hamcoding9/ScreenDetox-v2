package com.hamcoding.screendetox.ui.stats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hamcoding.screendetox.R
import com.hamcoding.screendetox.data.AppRepository
import com.hamcoding.screendetox.databinding.FragmentStatsBinding
import com.hamcoding.screendetox.ui.ViewModelFactory

class StatsFragment : Fragment() {
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatsViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
    }

    private fun setLayout() {
        val adapter = AppAdapter()
        binding.rvStats.adapter = adapter
        binding.statsTopBoard.tvUsageInfo.text = viewModel.totalUsage
        adapter.submitList(viewModel.appList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}