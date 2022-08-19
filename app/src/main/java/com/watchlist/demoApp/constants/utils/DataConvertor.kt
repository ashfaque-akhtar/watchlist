package com.indusnet.watchlist.common.util

import android.util.Log
import kotlin.random.Random


/**
 * Class helps in data manipulation
 */
object DataConvertor {


    fun getAbsoluteValue(data: Int): String {

        if (data >= 1000 && data <= 99999) {
            return (data / 1000).toString() + "K"
        }

        if (data >= 100000 && data <= 9999999) {
            return (data / 100000).toString() + "L"
        }

        if (data >= 10000000 && data <= 999999999) {
            return (data / 10000000).toString() + "C"
        }

        return data.toString()
    }


    fun getRandomNewTradePrice(tradePrice: Double): Double {
        if (Random.nextBoolean()) {
            return tradePrice + Random.nextInt(1, 500)
        }

        if (tradePrice > 10) {
            return tradePrice - Random.nextInt(1, 500)
        }

        return tradePrice
    }
}