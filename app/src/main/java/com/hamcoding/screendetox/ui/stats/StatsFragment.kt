package com.hamcoding.screendetox.ui.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hamcoding.screendetox.ScreenApplication
import com.hamcoding.screendetox.data.StatsRepository
import com.hamcoding.screendetox.databinding.FragmentStatsBinding
import com.hamcoding.screendetox.ui.TopBoardViewModel
import com.hamcoding.screendetox.ui.ViewModelFactory

class StatsFragment : Fragment() {
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatsViewModel by viewModels { ViewModelFactory() }
    private val boardViewModel: TopBoardViewModel by viewModels<TopBoardViewModel> {
        TopBoardViewModel.provideFactory(StatsRepository(ScreenApplication.usageProcessor))
    }

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
        adapter.submitList(viewModel.appList)
        binding.statsTopBoard.viewModel = boardViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}