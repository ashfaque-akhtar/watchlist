package com.watchlist.demoApp.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.watchlist.demoApp.R
import com.watchlist.demoApp.constants.Constants
import com.watchlist.demoApp.databinding.FragmentOptionsBottomSheetBinding

class OptionsBottomSheetFragment(private val onPositiveClick:()->Unit) : BottomSheetDialogFragment() {

    private var _binding: FragmentOptionsBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOptionsBottomSheetBinding.inflate(inflater, container, false)
        setupUIAndOptions(arguments?.getString("type")?:"")

        binding.btnPositive.setOnClickListener {
            onPositiveClick()
            dismiss()
        }

        binding.btnNegative.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun setupUIAndOptions(type:String){
        when(type){
            TYPE_LOGOUT->{
                binding.message.text="Do you want to logout?"
                binding.btnPositive.text="Logout"
                binding.btnNegative.text="Cancel"
            }

            Constants.LIST_VIEW->{
                binding.message.text="Do you want to switch to List View"
                binding.btnPositive.text="Switch"
                binding.btnNegative.text="Cancel"
            }

            Constants.GRID_VIEW->{
                binding.message.text="Do you want to switch to Grid View"
                binding.btnPositive.text="Switch"
                binding.btnNegative.text="Cancel"
            }
        }
    }

    companion object {

        const val TYPE_LOGOUT="typeLogout"
        fun newInstance(type:String,onPositiveClick: () -> Unit) =
            OptionsBottomSheetFragment(onPositiveClick).apply {
                arguments = Bundle().apply {
                    putString("type",type)
                }
            }

        const val TAG="OptionsBottomSheetFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}