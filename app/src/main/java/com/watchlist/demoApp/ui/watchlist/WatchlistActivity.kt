package com.watchlist.demoApp.ui.watchlist

import android.annotation.SuppressLint
import android.app.UiModeManager
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.watchlist.demoApp.R
import com.watchlist.demoApp.application.WatchListApplication
import com.watchlist.demoApp.constants.Constants
import com.watchlist.demoApp.data.DataStoreHelper
import com.watchlist.demoApp.data.model.WatchList
import com.watchlist.demoApp.databinding.ActivityWatchlistBinding
import com.watchlist.demoApp.ui.login.LoginActivity
import com.watchlist.demoApp.ui.login.LoginViewModel
import com.watchlist.demoApp.ui.watchlist.adapters.WatchlistPagerAdapter
import com.watchlist.demoApp.ui.watchlist.viewmodel.SharedViewModel
import kotlinx.coroutines.*


class WatchlistActivity : AppCompatActivity() {
    lateinit var binding: ActivityWatchlistBinding
    private val fragmentList = mutableListOf<Fragment>()
    private val uiModeManager: UiModeManager? = null
    var isFirst = true

    private lateinit var menu: Menu
    private lateinit var repeatJob: Job

    private val viewModel: WatchListViewModel by lazy {
        ViewModelProvider(this)[WatchListViewModel::class.java]
    }

    private lateinit var sharedViewModel: SharedViewModel
    private val viewModelLogin: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = resources.getString(R.string.watchlist)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        getWatchLists()
        attachListener()

    }

    private fun getWatchLists() {
        lifecycleScope.launch {
            setupWatchListTabs(viewModel.getWatchLists().reversed())
        }
    }


    //Setup WatchList Tabs
    private fun setupWatchListTabs(watchLists: List<WatchList>) {

        binding.tabLayout.removeAllTabs()

        if (watchLists.isEmpty()) return
        // val startItemPosition=(watchLists.size-1)
        for (element in watchLists) {
            val item = element
            binding.tabLayout.addTab(binding.tabLayout.newTab())
            fragmentList.add(WatchListFragment.newInstance(item.watchlistName))
        }
        val adapter = WatchlistPagerAdapter(this, fragmentList)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = watchLists[position].watchlistName
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.appbar_menu, menu)
        this.menu = menu
        setupSettings()
        return true
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return when (id) {
            R.id.increse -> {
                CoroutineScope(Dispatchers.Main).launch {

                    stopRefreshCounter()

                    val listOrder =
                        CoroutineScope(Dispatchers.IO).async { DataStoreHelper.getListOrder() }
                            .await()

                    var newListOrder = Constants.ORDER_ASCENDING
                    if (listOrder == Constants.ORDER_ASCENDING) {
                        newListOrder = Constants.ORDER_DESCENDING

                        menu.getItem(0).setIcon(resources.getDrawable(R.drawable.ic_trend_down))
                    } else {
                        menu.getItem(0).setIcon(resources.getDrawable(R.drawable.ic_trend_up))
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        DataStoreHelper.updateListOrder(
                            newListOrder
                        )
                    }

                    //sharedViewModel.changeOrder()
                    startRefreshCounter()

                }

                if (item.icon.equals(resources.getDrawable(R.drawable.ic_trend_up))) {
                    menu.getItem(0).setIcon(resources.getDrawable(R.drawable.ic_trend_up))
                } else {
                    menu.getItem(0).setIcon(resources.getDrawable(R.drawable.ic_trend_down))
                }
                true
            }
            R.id.list -> {
                var type = Constants.LIST_VIEW
                //Setting default list type is grid view
                lifecycleScope.launch {
                    if (DataStoreHelper.getListType() == Constants.LIST_VIEW) {
                        type = Constants.GRID_VIEW
                    }
                }

                OptionsBottomSheetFragment.newInstance(type) {
                    lifecycleScope.launch {
                        DataStoreHelper.updateListType(type)
                        if (type == Constants.GRID_VIEW) {

                            menu.getItem(1).setIcon(resources.getDrawable(R.drawable.ic_grid))
                        } else {
                            menu.getItem(1).setIcon(resources.getDrawable(R.drawable.ic_list))
                        }
                        sharedViewModel.changeLayout()
                    }

                }.apply {
                    show(supportFragmentManager, OptionsBottomSheetFragment.TAG)
                }
                true
            }
            R.id.filter -> {
                FilterBottomSheetFragment.newInstance() {
                    sharedViewModel.changeOrder()
                }.apply {
                    show(supportFragmentManager, FilterBottomSheetFragment.TAG)
                }
                true
            }
            R.id.logout -> {
                OptionsBottomSheetFragment.newInstance(OptionsBottomSheetFragment.TYPE_LOGOUT) {
                    logoutUser()
                }.apply {
                    show(supportFragmentManager, OptionsBottomSheetFragment.TAG)
                }
                true
            }

            R.id.dark_theme -> {
                WatchListApplication.instance.changeAppTheme(AppCompatDelegate.MODE_NIGHT_YES)
                return true
            }
            R.id.light_theme -> {
                WatchListApplication.instance.changeAppTheme(AppCompatDelegate.MODE_NIGHT_NO)
                return true
            }
            R.id.auto_theme -> {
                WatchListApplication.instance.changeAppTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun logoutUser() {
        lifecycleScope.launch {
            viewModelLogin.logout()
            startActivity(Intent(this@WatchlistActivity, LoginActivity::class.java))
            finishAffinity()
        }
    }


    private fun attachListener() {
        sharedViewModel.OnItemSelected().observe(this, Observer {
            DetailBottomSheetFragment.newInstance(it).apply {
                show(supportFragmentManager, DetailBottomSheetFragment.TAG)
            }
        })
    }

    /**
     * Initial Page setting for
     */
    private fun setupSettings() = CoroutineScope(Dispatchers.Main).launch {


        val listOrder =
            CoroutineScope(Dispatchers.IO).async { DataStoreHelper.getListOrder() }.await()

        if (listOrder == Constants.ORDER_ASCENDING) {
            menu.getItem(0).setIcon(resources.getDrawable(R.drawable.ic_trend_up))
        } else {
            menu.getItem(0).setIcon(resources.getDrawable(R.drawable.ic_trend_down))
        }


        val gridType =
            CoroutineScope(Dispatchers.IO).async { DataStoreHelper.getListType() }.await()

        if (gridType == Constants.GRID_VIEW) {
            menu.getItem(1).setIcon(resources.getDrawable(R.drawable.ic_grid))
        } else {
            menu.getItem(1).setIcon(resources.getDrawable(R.drawable.ic_list))
        }
    }

    private fun repeatFun(): Job {
        return lifecycleScope.launch {
            while (isActive) {
                sharedViewModel.updateData()
                delay(if (isFirst) 10 else 3000)
                isFirst = false
            }
        }
    }

    /**
     * Start refreshing job
     */
    private fun startRefreshCounter() {
        isFirst = true
        if (::repeatJob.isInitialized) {
            if (!repeatJob.isActive) {
                repeatJob = repeatFun()
            }
        } else {
            repeatJob = repeatFun()
        }

    }

    /**
     * Stop refreshing job
     */
    private fun stopRefreshCounter() {
        if (repeatJob.isActive) {
            isFirst = false
            repeatJob.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        startRefreshCounter()
    }

    override fun onStop() {
        super.onStop()
        stopRefreshCounter()
    }
}