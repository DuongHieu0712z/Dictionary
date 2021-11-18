package com.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dictionary.Database.SQLiteHelper;
import com.dictionary.Model.Word;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SQLiteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitialDB();
        showFrg(new SplastFragment());
    }
    private void showFrg(Fragment frg) {
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, frg,
                null).commit();
    }
    public void gotoM001Screen(){
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, new SearchWordFragment(),
                null).commit();
        setNotification();
        setNotificationByTime();
    }
    private void InitialDB(){
        helper = new SQLiteHelper(this);
        helper.openDB();
        helper.createTable();
    }
    public void setNotification(){
        ArrayList<Word> list = helper.getAll();
        if(!list.isEmpty()){
            Random random = new Random();
            int index = random.nextInt(list.size());
            Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                    .setContentTitle(list.get(index).getEngKey().toString()+"/"+list.get(index).getVieKey().toString())
                    .setContentText(list.get(index).getEngMean().toString()+"/"+list.get(index).getVieMean().toString())
                    .setSmallIcon(R.drawable.ic_baseline_speaker_notes_24)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager!=null){
                notificationManager.notify(1, notification);
            }
        }
    }
    private void setNotificationByTime(){
        try{
            Intent intent = new Intent(MainActivity.this,BroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long timeCurrent = System.currentTimeMillis();
            long tenSeccondInMillis = 1000*5;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 7);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 1);

            //alarmManager.set(AlarmManager.RTC_WAKEUP, timeCurrent+tenSeccondInMillis, pendingIntent);
        /*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);*/
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeCurrent,
                    tenSeccondInMillis, pendingIntent);
        }catch (Exception ex){}

    }
}
