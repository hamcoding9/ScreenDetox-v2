package com.hamcoding.screendetox.ui.rank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hamcoding.screendetox.R
import com.hamcoding.screendetox.ScreenApplication
import com.hamcoding.screendetox.data.db.repository.RankRepository
import com.hamcoding.screendetox.data.db.repository.StatsRepository
import com.hamcoding.screendetox.databinding.FragmentRankBinding
import com.hamcoding.screendetox.ui.common.UsageViewModel
import com.hamcoding.screendetox.ui.notification.NotificationActivity

class RankFragment : Fragment() {

    private var _binding: FragmentRankBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UsageViewModel> {
        UsageViewModel.provideFactory(
            RankRepository(),
            StatsRepository(ScreenApplication.usageProcessor)
        )
    }
    private val adapter = RankAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleToolbar()
    }

    private fun initView() {
        binding.apply {
            rvRank.adapter = adapter
            rankTopBoard.viewModel = viewModel
        }

        viewModel.rankingList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.rankNumber.observe(viewLifecycleOwner) {
            binding.rankTopBoard.tvUserRank.text = it.toString()
        }
    }

    private fun handleToolbar() {
        setToolbarClickListener()
        setNavigationClickListener()
    }

    private fun setToolbarClickListener() {
        binding.rankTopBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.rank_notification -> {
                    startActivity(Intent(activity, NotificationActivity::class.java))
                    Log.d("랭킹 화면", "notification 클릭")
                    true
                }
                else -> false
            }
        }
    }

    private fun setNavigationClickListener() {
        binding.rankTopBar.setNavigationOnClickListener { view ->
            showMenu(view, R.menu.rank_navigation_menu)
        }
    }

    private fun showMenu(view: View?, menuRes: Int) {
        val popup = PopupMenu(activity, view)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.rankNaviAddFriend -> {
                    showDialogRequestFriend()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun showDialogRequestFriend() {
        val action = RankFragmentDirections.actionNavigationRankToDialogRequestFriend()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}