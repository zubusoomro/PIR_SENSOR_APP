package com.zubu.soft.mobile.data.detection.appInterfaces

import com.zubu.soft.mobile.data.detection.models.SensorModel

interface IGattInterface {
    fun onGotDevice(model: SensorModel)
}