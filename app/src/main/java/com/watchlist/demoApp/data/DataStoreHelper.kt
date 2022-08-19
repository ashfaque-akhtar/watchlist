package com.watchlist.demoApp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.watchlist.demoApp.application.WatchListApplication
import com.watchlist.demoApp.constants.Constants
import kotlinx.coroutines.flow.first

/**
 * local cache files stored here
 */
object DataStoreHelper {

    val KEY_USER_LOGIN_STATUS = booleanPreferencesKey("keyUserLoginStatus")
    val KEY_USER_EMAIL = stringPreferencesKey("keyUserEmail")
    val KEY_LIST_TYPE = stringPreferencesKey("keyListType")
    val KEY_LIST_ORDER = stringPreferencesKey("keyListOrder")
    val KEY_FILTER_STATUS = booleanPreferencesKey("keyFilterStatus")

    val KEY_FILTER_DAY_HIGH = stringPreferencesKey("keyFilterDayHigh")
    val KEY_FILTER_DAY_LOW = stringPreferencesKey("keyFilterDayLow")
    val KEY_FILTER_NSE_BSE_CODE = stringPreferencesKey("keyFilterNseBseCode")
    val KEY_FILTER_SCRIPT_CODE = stringPreferencesKey("keyFilterScriptCode")
    val APP_THEME = intPreferencesKey("appTheme")


    val KEY_SORT_PARAMETER = stringPreferencesKey("keySortParameter")

    private val dataStore: DataStore<Preferences> = WatchListApplication.instance.getDataStore()

    //Save Operation
    private suspend fun <T> saveData(keyValue: Map<Preferences.Key<T>, T>) {
        dataStore.edit {
            val iterator = keyValue.iterator()
            while (iterator.hasNext()) {
                val data = iterator.next()
                it[data.key] = data.value
            }
        }
    }

    //Read Operation
    private suspend fun <T> readData(key: Preferences.Key<T>): T? {
        val preferences = dataStore.data.first()
        return preferences[key]
    }

    //Clear Data
    suspend fun clearData() {
        dataStore.edit {
            it.clear()
        }
    }

    /* -- USER DATA -- */
    suspend fun saveUserData(email: String) {
        val data = mutableMapOf<Preferences.Key<String>, String>()
        data[KEY_USER_EMAIL] = email
        saveData(data)
    }

    suspend fun saveAppTheme(theme: Int) {
        val data = mutableMapOf<Preferences.Key<Int>, Int>()
        data[APP_THEME] = theme
        saveData(data)
    }

    /* -- LOGIN STATUS -- */
    //Update loginStatus
    suspend fun updateUserLoginStatus(status: Boolean) {
        val data = mutableMapOf<Preferences.Key<Boolean>, Boolean>()
        data[KEY_USER_LOGIN_STATUS] = status
        saveData(data)
    }

    //Get loginStatus
    suspend fun isUserLoggedIn(): Boolean {
        return readData(KEY_USER_LOGIN_STATUS) ?: false
    }


    suspend fun getAppTheme(): Int {
        return readData(APP_THEME) ?: -1
    }

    /* -- LIST VIEW -- */
    //Update List Type
    suspend fun updateListType(listType: String) {
        val data = mutableMapOf<Preferences.Key<String>, String>()
        data[KEY_LIST_TYPE] = listType
        saveData(data)
    }

    //Get List Type
    suspend fun getListType(): String? {
        return readData(KEY_LIST_TYPE) ?: Constants.LIST_VIEW
    }

    /* -- LIST ORDER -- */
    //Update List Order
    suspend fun updateListOrder(order: String) {
        val data = mutableMapOf<Preferences.Key<String>, String>()
        data[KEY_LIST_ORDER] = order
        saveData(data)
    }

    //Get List Order
    suspend fun getListOrder(): String {
        return readData(KEY_LIST_ORDER) ?: Constants.ORDER_ASCENDING
    }

    /* -- FILTER -- */
    //Update List Type
    suspend fun updateFilterStatus(status: Boolean) {
        val data = mutableMapOf<Preferences.Key<Boolean>, Boolean>()
        data[KEY_FILTER_STATUS] = status
        saveData(data)
    }

    //Get List Type
    suspend fun getFilterStatus(): Boolean {
        return readData(KEY_FILTER_STATUS) ?: false
    }

    /* -- FILTER -- */
    //Update Sort Parameter
    suspend fun updateSortParameter(parameter: String) {
        val data = mutableMapOf<Preferences.Key<String>, String>()
        data[KEY_SORT_PARAMETER] = parameter
        saveData(data)
    }

    //Get Sort Parameter
    suspend fun getSortParameter(): String {
        return readData(KEY_SORT_PARAMETER) ?: Constants.KEY_VOLUME
    }

    suspend fun updateFilterData(
        dayHigh: String,
        dayLow: String,
        nseBseCode: String,
        scriptCode: String
    ) {
        val data = mutableMapOf<Preferences.Key<String>, String>()
        data[KEY_FILTER_DAY_HIGH] = dayHigh
        data[KEY_FILTER_DAY_LOW] = dayLow
        data[KEY_FILTER_NSE_BSE_CODE] = nseBseCode
        data[KEY_FILTER_SCRIPT_CODE] = scriptCode
        saveData(data)
    }

    suspend fun getFilterData(): Map<String, String> {
        val data = mutableMapOf<String, String>()

        data[Constants.KEY_VOLUME] = readData(KEY_FILTER_DAY_HIGH) ?: ""
        data[Constants.KEY_PCLOSE] = readData(KEY_FILTER_DAY_LOW) ?: ""
        data[Constants.KEY_TRADE_PRICE] = readData(KEY_FILTER_NSE_BSE_CODE) ?: ""
        data[Constants.KEY_SCRIPT_CODE] = readData(KEY_FILTER_SCRIPT_CODE) ?: ""

        return data
    }

    suspend fun clearFilter() {
        val data = mutableMapOf<Preferences.Key<String>, String>()
        data[KEY_FILTER_DAY_HIGH] = ""
        data[KEY_FILTER_DAY_LOW] = ""
        data[KEY_FILTER_NSE_BSE_CODE] = ""
        data[KEY_FILTER_SCRIPT_CODE] = ""
        saveData(data)
    }

}