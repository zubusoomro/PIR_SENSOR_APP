package com.zubu.soft.mobile.data.detection.appInterfaces

interface GattConnectivityCallback {

    fun onConnected(success: Boolean)
    fun onReadData(data: String)
}