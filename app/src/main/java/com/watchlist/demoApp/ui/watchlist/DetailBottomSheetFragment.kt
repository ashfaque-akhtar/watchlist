package com.watchlist.demoApp.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.watchlist.demoApp.R
import com.watchlist.demoApp.data.model.TradeDetail
import com.watchlist.demoApp.databinding.FragmentDetailBottomSheetBinding

class DetailBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var tradeDetail: TradeDetail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBottomSheetBinding.inflate(inflater, container, false)
        setData(arguments?.getSerializable("data") as TradeDetail)
        return binding.root
    }

    companion object {
        fun newInstance(data: TradeDetail): DetailBottomSheetFragment =
            DetailBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("data", data)
                }
            }

        const val TAG = "DetailBottomSheetFragment"
    }

    fun setData(data: TradeDetail) {
        tradeDetail = data

        binding.dayHigh.text = tradeDetail.ShortName.toString()
        binding.name.text = tradeDetail.DayHigh.toString()
        binding.dayLow.text = tradeDetail.DayLow.toString()
        binding.nseBseCode.text = tradeDetail.NseBseCode.toString()
        binding.scripCode.text = tradeDetail.ScripCode.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}