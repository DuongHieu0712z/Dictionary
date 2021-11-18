package com.dictionary;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dictionary.Database.SQLiteHelper;
import com.dictionary.Model.Word;

import java.util.ArrayList;
import java.util.Random;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SQLiteHelper helper = new SQLiteHelper(context);
        helper.openDB();
        ArrayList<Word> list = helper.getAll();
        if(!list.isEmpty()){
            Random random = new Random();
            int index = random.nextInt(list.size());

            Notification notification = new NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                    .setContentTitle(list.get(index).getEngKey().toString()+"/"+list.get(index).getVieKey().toString())
                    .setContentText(list.get(index).getEngMean().toString()+"/"+list.get(index).getVieMean().toString())
                    .setSmallIcon(R.drawable.ic_baseline_speaker_notes_24)
                    .build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager!=null){
                notificationManager.notify(1, notification);
            }
        }

        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification_id")
                .setSmallIcon(R.drawable.ic_baseline_speaker_notes_24)
                .setContentTitle(list.get(index).getEnglish().toString())
                .setContentText(list.get(index).getVietNamese().toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(1, builder.build());*/
    }
}
