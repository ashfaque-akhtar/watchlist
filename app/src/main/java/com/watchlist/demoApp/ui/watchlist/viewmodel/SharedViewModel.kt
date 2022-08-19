package com.watchlist.demoApp.ui.watchlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.watchlist.demoApp.data.model.TradeDetail

class SharedViewModel() : ViewModel() {
    val selectedItem= MutableLiveData<TradeDetail>()
    val layoutViewChange= MutableLiveData<Boolean>()
    val orderChange= MutableLiveData<Boolean>()
    val refreshData= MutableLiveData<Boolean>()
    val filterStatus= MutableLiveData<Boolean>()


    fun OnItemSelected(): LiveData<TradeDetail> {
        return selectedItem
    }

    fun OnLayoutViewChange(): LiveData<Boolean> {
        return layoutViewChange
    }

    fun OnOrderChange(): LiveData<Boolean> {
        return orderChange
    }

    fun OnRefreshData(): LiveData<Boolean> {
        return refreshData
    }

    fun OnFilterStatusChanged(): LiveData<Boolean> {
        return filterStatus
    }

    fun selectItem(data: TradeDetail){
        selectedItem.value=data
    }

    fun changeLayout(){
        layoutViewChange.value=true
    }

    fun changeOrder(){
        orderChange.value=true
    }

    fun updateData(){
        refreshData.value=true
    }

    fun updateFilterStatus(status:Boolean){
        filterStatus.value=status
    }
}