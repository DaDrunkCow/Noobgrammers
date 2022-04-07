package com.yorku.noobgrammers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.AlarmManager;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnAlarmListener {

    private static final String TAG = "MainActivity";

    private ArrayList<Alarm> alarmList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recyclerview functionality
        recyclerView = findViewById(R.id.scroll);
        alarmList = new ArrayList<>();
        adapter = new RecyclerAdapter(alarmList, this);

        // add alarms from saved data
        retrieveStoredAlarms();

        // setup adapter
        setAdapter();

        // notification helper
        mNotificationHelper = new NotificationHelper(this);
    }

    // recycler methods
    private void setAdapter ()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    // create method to add alarms to alarm list
    public void addAlarm (Alarm alarm)
    {
        activateAlarms(alarm, true);
        alarmList.add(alarm);
        adapter.notifyItemInserted(alarmList.size() - 1);
    }

    // method to update alarm
    public void updateAlarm (int position, Alarm alarm)
    {
        activateAlarms(alarmList.get(position), false);
        activateAlarms(alarm, true);
        alarmList.set(position, alarm);
        adapter.notifyItemChanged(position);
    }

    // method to remove alarm
    public void removeAlarm (int position)
    {
        activateAlarms(alarmList.get(position), false);
        alarmList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    // create alarm button touch
    ActivityResultLauncher activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Intent intent = result.getData();
                    if (intent != null) {
                        String[] alarm = intent.getStringArrayExtra("alarm");
                        if (result.getResultCode() != -2) {
                            Alarm temp = new Alarm(alarm[0], alarm[1], alarm[2]);
                            if (result.getResultCode() == RESULT_OK) {
                                addAlarm(temp);
                            } else {
                                updateAlarm(result.getResultCode(), temp);
                            }
                        }
                        else
                        {
                            Log.d(TAG, "onActivityResult: " + intent.getIntExtra("position", -1));
                            removeAlarm(intent.getIntExtra("position", -1));
                        }
                    }

                }
            });

    // open an alarm creation menu
    public void openCreate (View view)
    {
        createMenu(null, -1);
    }

    // open an alarm editing menu
    @Override
    public void onAlarmClick(int position)
    {
        createMenu(alarmList.get(position), position);
    }

    // create method to open create with parameters
    public void createMenu (Alarm alarm, int position)
    {
        Intent intent = new Intent(MainActivity.this, Pop.class);
        if (alarm != null)
        {
            intent.putExtra("alarm", alarm.getAlarm());
            intent.putExtra("position", position);
        }
        else
        {
            // TODO: might want to delete this or change this, this method of filling blanks seems poorly done
            intent.putExtra("alarm", new Alarm("00:00", "", "0000000").getAlarm());
        }
        activityLauncher.launch(intent);
    }

    // store the alarm data
    @Override
    public void onStop ()
    {
        super.onStop();
        SharedPreferences settings = getApplicationContext().getSharedPreferences("alarmData", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("count", alarmList.size());
        for (int i = 0; i < alarmList.size(); i++)
        {
            String[] info = alarmList.get(i).getAlarm();
            editor.putString(i + "time", info[0]);
            editor.putString(i + "name", info[1]);
            editor.putString(i + "period", info[2]);
        }
        editor.apply();
    }

    // method to update alarm list with stored data
    public void retrieveStoredAlarms ()
    {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("alarmData", 0);
        int count = settings.getInt("count", 0);
        for (int i = 0; i < count; i++)
        {
            addAlarm(new Alarm(
                    settings.getString(i + "time", "00:00"),
                    settings.getString(i + "name", ""),
                    settings.getString(i + "period", "0000000")));
        }
    }

    // create method to set what time the alarm will fire each time of the week
    public void setTime (int weekDay, int hourOfDay, int minute, boolean add)
    {
        /*
         * weekDay parameter
         * 0 = sunday
         * 1 = monday
         * 2 = tuesday
         * 3 = wednesday
         * 4 = thursday
         * 5 = friday
         * 6 = saturday
         */
        Calendar c = Calendar.getInstance();

        // adjust the date of the alarm based on current time
        c.set(Calendar.DAY_OF_WEEK, weekDay + 1);

        // if the date is before the current day, put it at next week
        if (c.before(Calendar.getInstance()))
        {
            c.add(Calendar.DATE, 7);
        }

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        if (add)
        {
            startAlarm(c);
        }
        else
        {
            cancelAlarm(c);
        }
    }

    private void startAlarm (Calendar c)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY*7,
                pendingIntent);
    }

    private void cancelAlarm (Calendar c)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    private void activateAlarms (Alarm alarm, boolean active)
    {
        boolean[] repeat = alarm.getAllRepeat();
        for (int i = 0; i < repeat.length; i++)
        {
            if (repeat[i])
            {
                setTime(i, alarm.getTime()[0], alarm.getTime()[1], active);
            }
        }
    }

}