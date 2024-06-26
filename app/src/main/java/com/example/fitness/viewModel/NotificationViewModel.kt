package com.example.fitness.viewModel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fitness.util.DiaryHelper
import com.example.fitness.util.JobHelper
import com.example.fitness.util.JobHelper.Companion.calculatePeriod

class NotificationViewModel(app: Application) : AndroidViewModel(app) {
    var isNotificationOn = MutableLiveData<String>() // notification status
    private var initialHour = MutableLiveData<Int>()
    private var finalHour = MutableLiveData<Int>()

    private val context = app
    private val jobHelper = JobHelper

    init {
        isNotificationOn.value = jobHelper.getNotificationStatus(context)
    }

    fun updateHours() {
        JobHelper.updateHours(context)
        initialHour.value = JobHelper.initialHour
        finalHour.value = JobHelper.finalHour
    }

    fun getInitialHour() = initialHour.value ?: 8

    fun getFinalHour() = finalHour.value ?: 24

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateInterval() {
        JobHelper.updateInterval(context, getInitialHour(), getFinalHour())
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun calculatePeriod(defaultValue: Boolean = true): Float {
        return if (defaultValue)
            calculatePeriod(context, 2000F)
        else
            calculatePeriod(context, DiaryHelper.getTotalWaterML(context))
    }

    fun changeNotificationStatus() {
        jobHelper.changeNotificationStatus(context)
        publishStatus()
    }

    private fun publishStatus() {
        isNotificationOn.value = jobHelper.getNotificationStatus(context)
    }

    // change hours to milliseconds
    fun convertToMilliseconds(number: Float) = number * 60 * 60
}