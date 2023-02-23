package com.hamcoding.screendetox.ui.rank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hamcoding.screendetox.R
import com.hamcoding.screendetox.ScreenApplication
import com.hamcoding.screendetox.data.*
import com.hamcoding.screendetox.databinding.FragmentRankBinding
import com.hamcoding.screendetox.ui.TopBoardViewModel
import com.hamcoding.screendetox.ui.notification.NotificationActivity

class RankFragment : Fragment() {

    private var _binding: FragmentRankBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RankViewModel> {
        RankViewModel.provideFactory(RankRepository())
    }
    private val boardViewModel: TopBoardViewModel by viewModels<TopBoardViewModel> {
        TopBoardViewModel.provideFactory(StatsRepository(ScreenApplication.usageProcessor))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        viewModel.loadFriendList()
        val adapter = RankAdapter()
        binding.rvRank.adapter = adapter
        adapter.submitList(viewModel.items)
        binding.rankTopBoard.viewModel = boardViewModel
        binding.rankTopBoard.tvUserRank.text = viewModel.rankNumber.toString()
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
        findNavController().navigate(R.id.action_navigation_rank_to_dialogRequestFriend)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}