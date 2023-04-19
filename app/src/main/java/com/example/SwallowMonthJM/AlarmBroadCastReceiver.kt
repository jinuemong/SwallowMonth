package com.example.SwallowMonthJM

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity
import org.mozilla.javascript.tools.jsc.Main
import java.time.LocalDateTime
import java.util.Calendar

// 응답을 받는 역할
// 알람 매니저를 통해 트리거 시간과 반복 시간, 생성한 브로드캐스트 리시버를 세팅

class AlarmBroadCastReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(p0: Context?, p1: Intent?) {
        // 알람을 받는 곳
        if (p0!=null && p1!=null) {
            when(p1.extras?.get("code")){
                MainActivity.REQUEST_CODE_1->{ // main 리셋
                    Toast.makeText(p0, "init current Data", Toast.LENGTH_SHORT).show()
                    Log.d("initCurrentData", LocalDateTime.now().toString())
                    val code = MainActivity.REQUEST_CODE_1
                    // 메인으로 이동하는 인텐트 생성
                    val intent = Intent(p0, MainActivity::class.java).apply {
                        putExtra("username", p1.extras?.get("username").toString())
                    }

                    // 늦은 인텐트로 전환
                    val pendingIntent = PendingIntent.getActivity(p0, 0, intent, 0)
                    // 알림 생성 - 메시지 전달
                    val builder = NotificationCompat.Builder(p0, "my_channel")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("[Swallow Notification]")
                        .setContentText("update data today!")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                    // 알림 실행
                    NotificationManagerCompat.from(p0).apply {
                        notify(code, builder.build())
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            "my_channel", "Notification",
                            NotificationManager.IMPORTANCE_DEFAULT
                        ).apply {
                            description = "Notification"
                        }
                        val notificationManager =
                            p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.createNotificationChannel(channel)
                    }
                }
                MainActivity.REQUEST_CODE_2->{ //데이터 불러오기
                    Toast.makeText(p0, "initMonthData", Toast.LENGTH_SHORT).show()
                    Log.d("initMonthData", LocalDateTime.now().toString())
                    val code = MainActivity.REQUEST_CODE_2
                    // 메인으로 이동하는 인텐트 생성
                    val intent = Intent(p0, MainActivity::class.java).apply {
                        putExtra("username", p1.extras?.get("username").toString())
                        putExtra("fragment","recently")
                    }

                    // 늦은 인텐트로 전환
                    val pendingIntent = PendingIntent.getActivity(p0, 0, intent, 0)
                    // 알림 생성 - 메시지 전달
                    val builder = NotificationCompat.Builder(p0, "my_channel_2")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("[Swallow Notification_2]")
                        .setContentText("update month data!")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true) //클릭 시 삭제
                    // 알림 실행
                    NotificationManagerCompat.from(p0).apply {
                        notify(code, builder.build())
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            "my_channel_2", "Notification_2",
                            NotificationManager.IMPORTANCE_DEFAULT // 알림음 있음
                        ).apply {
                            description = "Notification_2"
                        }
                        val notificationManager =
                            p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.createNotificationChannel(channel)
                    }
                }
            }
        }
    }

}

