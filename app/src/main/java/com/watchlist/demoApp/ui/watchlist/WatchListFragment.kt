package com.watchlist.demoApp.ui.watchlist

import android.R.animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.watchlist.demoApp.constants.Constants
import com.watchlist.demoApp.constants.Constants.GRID_VIEW
import com.watchlist.demoApp.constants.Constants.LIST_VIEW
import com.watchlist.demoApp.data.DataStoreHelper
import com.watchlist.demoApp.databinding.FragmentWatchListBinding
import com.watchlist.demoApp.ui.watchlist.adapters.WatchListAdapter
import com.watchlist.demoApp.ui.watchlist.viewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


private const val PARAM1 = "watchList"

class WatchListFragment : Fragment() {
    private var WATCHLIST_NAME: String? = null
    lateinit var binding: FragmentWatchListBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var watchListAdapter: WatchListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private val viewModel: WatchListViewModel by lazy {
        ViewModelProvider(this)[WatchListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            WATCHLIST_NAME = it.getString(PARAM1)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchListBinding.inflate(inflater, container, false)

        sharedViewModel = activity?.run {
            ViewModelProvider(this)[SharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        setRecyclerView()
        addObserver()
        return binding.root
    }

    fun addObserver() = CoroutineScope(Dispatchers.Main).launch {
        viewModel.tradeDetailsData.observe(viewLifecycleOwner) {
            watchListAdapter.updateWatchList(it)
        }
        sharedViewModel.OnOrderChange().observe(viewLifecycleOwner, Observer {
            loadWatchlistData()
        })

        sharedViewModel.OnRefreshData().observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
//                viewModel.refreshWatchListData(watchListName)
                loadWatchlistData()
            }
        })
        sharedViewModel.OnLayoutViewChange().observe(viewLifecycleOwner, Observer {
            changeListType()
        })
    }

    override fun onResume() {
        super.onResume()
        loadWatchlistData()
    }

    private fun loadWatchlistData() {
        CoroutineScope(Dispatchers.IO).launch {
            val listOrder = DataStoreHelper.getListOrder()
            val sortParams = DataStoreHelper.getSortParameter()
            val isAsc = listOrder == Constants.ORDER_ASCENDING

            viewModel.loadWatchListData(WATCHLIST_NAME ?: "", isAsc, sortParams)

        }
    }

    private fun setRecyclerView() = CoroutineScope(Dispatchers.Main).launch {
        linearLayoutManager = LinearLayoutManager(this@WatchListFragment.context)
        gridLayoutManager = GridLayoutManager(this@WatchListFragment.context, 2)
        watchListAdapter = WatchListAdapter { tradeDetail ->
            sharedViewModel.selectItem(tradeDetail)
        }
        var layoutManager: RecyclerView.LayoutManager = linearLayoutManager
        val layoutType: String? =
            CoroutineScope(Dispatchers.IO).async { DataStoreHelper.getListType() }.await()
        if (!layoutType.isNullOrEmpty() && !layoutType.equals(LIST_VIEW))
            layoutManager = gridLayoutManager
        binding.rvWatchListData.apply {
            this.layoutManager = layoutManager
            this.adapter = watchListAdapter
        }
        val animator = binding.rvWatchListData.itemAnimator!!
        if (animator is SimpleItemAnimator) {
            (animator as SimpleItemAnimator).supportsChangeAnimations = false
        }

    }


    fun changeListType() = CoroutineScope(Dispatchers.Main).launch {
        val layoutType: String? =
            CoroutineScope(Dispatchers.IO).async { DataStoreHelper.getListType() }.await()

        if (layoutType == LIST_VIEW) {
            binding.rvWatchListData.layoutManager = linearLayoutManager
            CoroutineScope(Dispatchers.IO).launch {
                DataStoreHelper.updateListOrder(LIST_VIEW)
            }
            watchListAdapter.notifyDataSetChanged()
        } else {
            binding.rvWatchListData.layoutManager = gridLayoutManager
            CoroutineScope(Dispatchers.IO).launch {
                DataStoreHelper.updateListOrder(GRID_VIEW)
            }
            watchListAdapter.notifyDataSetChanged()
        }


    }


    companion object {
        fun newInstance(data: String) =
            WatchListFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM1, data.replace(" ", ""))
                }
            }
    }

}