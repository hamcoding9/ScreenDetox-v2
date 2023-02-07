package com.hamcoding.screendetox.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hamcoding.screendetox.R
import com.hamcoding.screendetox.databinding.DialogRequestFriendBinding

class DialogRequestFriend : DialogFragment() {

    private val viewModel by viewModels<DialogRequestFriendViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogRequestFriendBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.isCancel.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        viewModel.isSubmit.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        viewModel.isInvalidEmail.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_dialogRequestFriend_to_dialogRequestFriendFail)
        }
    }

}