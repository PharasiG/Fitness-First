package com.example.fitness.dao

import android.content.ContentValues
import android.content.Context
import com.example.fitness.model.Notification
import com.example.fitness.util.FINAL_HOUR
import com.example.fitness.util.INITIAL_HOUR
import com.example.fitness.util.IS_NOTIFICATION_ON
import com.example.fitness.util.NOTIFICATION_TABLE

class NotificationDao(context: Context) {
    private val dbHelper = DatabaseCreator(context)

    fun getNotificationSetting(): Notification {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(IS_NOTIFICATION_ON, INITIAL_HOUR, FINAL_HOUR)

        val cursor = db?.query(
            NOTIFICATION_TABLE, projection, null, null,
            null, null, null
        )

        var notification: Notification? = null
        with(cursor!!)
        {
            while (moveToNext()) {
                val isNotificationOn = getString(getColumnIndexOrThrow(IS_NOTIFICATION_ON))
                val initialHour = getInt(getColumnIndexOrThrow(INITIAL_HOUR))
                val finalHour = getInt(getColumnIndexOrThrow(FINAL_HOUR))

                notification = Notification(isNotificationOn, initialHour, finalHour)
            }
        }

        cursor.close()
        return notification!!
    }

    fun update(notification: Notification) {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put(IS_NOTIFICATION_ON, notification.isNotificationOn)
            put(INITIAL_HOUR, notification.initialHour)
            put(FINAL_HOUR, notification.finalHour)
        }

        db?.update(NOTIFICATION_TABLE, contentValues, null, null)
    }
}