package com.watchlist.demoApp.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.watchlist.demoApp.R
import com.watchlist.demoApp.constants.Constants
import com.watchlist.demoApp.constants.Constants.KEY_PCLOSE
import com.watchlist.demoApp.constants.Constants.KEY_TRADE_PRICE
import com.watchlist.demoApp.constants.Constants.KEY_VOLUME
import com.watchlist.demoApp.data.DataStoreHelper
import com.watchlist.demoApp.databinding.FragmentFilterBottomSheetBinding
import com.watchlist.demoApp.ui.watchlist.viewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class FilterBottomSheetFragment(private val onPositiveClick: () -> Unit) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val sortParams = arrayListOf<String>()
    private val sortParamsFields = arrayListOf<TextView>()

    private  lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.Main).launch {
            val presentFilterType = withContext(Dispatchers.IO){DataStoreHelper.getSortParameter()}
            if(presentFilterType.equals(KEY_VOLUME))
                binding.volume.setTextColor(context?.resources!!.getColor(R.color.color_primary))
            else if (presentFilterType.equals(KEY_PCLOSE))
                binding.pclose.setTextColor(context?.resources!!.getColor(R.color.color_primary))
            else if(presentFilterType.equals(KEY_TRADE_PRICE))
                binding.lastTradePrice.setTextColor(context?.resources!!.getColor(R.color.color_primary))
        }

        sharedViewModel=activity?.run {
            ViewModelProvider(this)[SharedViewModel::class.java]
        }?: throw Exception("Invalid Activity")

        addSortKey()
        setupOnClickEvents()
        return binding.root
    }

    private fun addSortKey() {
        sortParamsFields.add(binding.volume)
        sortParams.add(Constants.KEY_VOLUME)
        sortParamsFields.add(binding.pclose)
        sortParams.add(Constants.KEY_PCLOSE)
        sortParamsFields.add(binding.lastTradePrice)
        sortParams.add(Constants.KEY_TRADE_PRICE)
    }


    private fun setupOnClickEvents() {
        for(position in sortParamsFields.indices){
            sortParamsFields[position].setOnClickListener {
                lifecycleScope.launch {
                    DataStoreHelper.updateSortParameter(sortParams[position])
                  //  updateFilterStatus(true)
                    onPositiveClick()
                    dismiss()
                }
            }
        }
    }




    companion object {
        fun newInstance(onPositiveClick: () -> Unit) =
            FilterBottomSheetFragment(onPositiveClick).apply {
                arguments = Bundle().apply {

                }
            }

        const val TAG = "FilterBottomSheetFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}