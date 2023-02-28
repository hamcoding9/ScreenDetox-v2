package com.hamcoding.screendetox.ui.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hamcoding.screendetox.ScreenApplication
import com.hamcoding.screendetox.data.db.repository.RankRepository
import com.hamcoding.screendetox.data.db.repository.StatsRepository
import com.hamcoding.screendetox.databinding.FragmentStatsBinding
import com.hamcoding.screendetox.ui.common.UsageViewModel

class StatsFragment : Fragment() {
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UsageViewModel> {
        UsageViewModel.provideFactory(
            RankRepository(),
            StatsRepository(ScreenApplication.usageProcessor)
        )
    }
    private val adapter = AppAdapter()

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
        observeViewModel()
        initView()
    }

    private fun initView() {
        binding.apply {
            rvStats.adapter = adapter
            statsTopBoard.viewModel = viewModel
        }
        viewModel.loadAppList()
        viewModel.loadRankingList()
    }

    private fun observeViewModel() {
        viewModel.appList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.rankNumber.observe(viewLifecycleOwner) {
            binding.statsTopBoard.tvUserRank.text = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}