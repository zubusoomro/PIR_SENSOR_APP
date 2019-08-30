package com.zubu.soft.mobile.data.detection.appInterfaces

import com.zubu.soft.mobile.data.detection.models.SensorModel

interface DeviceClickListener {

    fun onConnect(model: SensorModel)

    fun onDisconnect(deviceAddress: String)
}