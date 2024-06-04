package com.example.fitness.job

import android.app.job.JobParameters
import android.app.job.JobService
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.fitness.MainActivity
import com.example.fitness.R
import com.example.fitness.notification.NotificationService
import com.example.fitness.util.DiaryHelper
import com.example.fitness.util.JobHelper
import com.example.fitness.util.NOTIFICATION_SUB_TITLE
import com.example.fitness.util.NOTIFICATION_TITLE

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DiaryJobService : JobService() {
    private val notificationService = NotificationService(applicationContext)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartJob(params: JobParameters?): Boolean {

        if (DiaryHelper.isGoalAccomplished(applicationContext))
            return true

        if (JobHelper.isIntervalValid(JobHelper.initialHour, JobHelper.finalHour) &&
            JobHelper.verifyInterval(JobHelper.initialHour, JobHelper.finalHour)
        ) {
            notificationService
                .pushNotification(
                    NOTIFICATION_TITLE,
                    NOTIFICATION_SUB_TITLE,
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                    R.mipmap.ic_launcher,
                    MainActivity::class.java
                )
        }

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }
}