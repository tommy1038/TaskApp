package jp.techacademy.tomiyama.ryota.taskapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import io.realm.Realm;

public class TaskAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        android.util.Log.d("TaskApp", "onReceive");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // SDKバージョンが26以上の場合、チャネルを設定する必要がある
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            notificationManager.createNotificationChannel(channel);
        }

        // 通知の設定を行う
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setSmallIcon(R.drawable.small_icon);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.large_icon));
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);

        // EXTRA_TASK から Task の id を取得して、 id から Task のインスタンスを取得する
        int taskId = intent.getIntExtra(MainActivity.EXTRA_TASK, -1);
        Realm realm = Realm.getDefaultInstance();
        Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();

        // タスクの情報を設定する
        builder.setTicker(task.getTitle()); // 5.0以降は表示されない
        builder.setContentTitle(task.getTitle());
        builder.setContentText(task.getContents());

        // 通知をタップしたらアプリを起動するようにする
        Intent startAppIntent = new Intent(context, MainActivity.class);
        startAppIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, startAppIntent, 0);
        builder.setContentIntent(pendingIntent);

        // 通知を表示する
        notificationManager.notify(task.getId(), builder.build());
        realm.close();
    }
}
