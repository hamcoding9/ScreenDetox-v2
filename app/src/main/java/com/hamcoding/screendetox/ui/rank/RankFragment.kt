package com.hamcoding.screendetox.ui.rank

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.PopupMenu
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.marginStart
import androidx.databinding.adapters.ViewBindingAdapter.setClickListener
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.hamcoding.screendetox.R
import com.hamcoding.screendetox.databinding.FragmentRankBinding

class RankFragment : Fragment() {

    private var _binding: FragmentRankBinding? = null
    private val binding get() = _binding!!
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
        handleToolbar()
    }

    private fun handleToolbar() {
        setToolbarClickListener()
        setNavigationClickListener()
    }

    private fun setToolbarClickListener() {
        binding.rankTopBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.rank_notification -> {
                    //TODO("notification 처리")
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
                    Log.d("랭킹 화면", "Add Friend 메뉴 클릭")
                    showDialogAddFriend()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun showDialogAddFriend() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("친구 요청")
            .setView(EditText(requireContext()).apply {
                hint = "이메일 주소"
            })
            .setPositiveButton("확인") { dialog, which ->
                Log.d("랭킹화면", "이메일 주소 입력 후 확인 클릭")
                //TODO("친구 신청하는 로직 추가")
            }
            .setNegativeButton("취소") { dialog, which ->

            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}