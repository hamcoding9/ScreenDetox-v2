package com.hamcoding.screendetox.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.hamcoding.screendetox.R
import com.hamcoding.screendetox.databinding.DialogRequestFriendFailBinding

class DialogRequestFriendSuccess : DialogFragment() {

    private var _binding: DialogRequestFriendFailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRequestFriendFailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvInfo.text = "요청이 완료되었습니다"
        binding.BtnPositive.setOnClickListener {
            findNavController().navigate(R.id.action_dialogRequestFriendSuccess_pop)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}