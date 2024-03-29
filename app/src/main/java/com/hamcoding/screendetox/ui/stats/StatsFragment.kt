package com.hamcoding.screendetox.ui.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hamcoding.screendetox.ScreenApplication
import com.hamcoding.screendetox.data.firebase.repository.RankRepository
import com.hamcoding.screendetox.data.firebase.repository.StatsRepository
import com.hamcoding.screendetox.databinding.FragmentStatsBinding
import com.hamcoding.screendetox.ui.common.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : Fragment() {
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
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
        viewModel.loadRankingList()
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            rvStats.adapter = adapter
            statsTopBoard.viewModel = viewModel
        }
        viewModel.loadAppList()
    }

    private fun observeViewModel() {
        viewModel.appList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.rankNumber.observe(viewLifecycleOwner) {
            binding.statsTopBoard.tvUserRank.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}