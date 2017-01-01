package com.example.test.langlarm.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.test.langlarm.R;
import com.example.test.langlarm.alarm.Alarm;
import com.example.test.langlarm.gags.GagsProvider;
import com.example.test.langlarm.persistence.PersistanceKeys;
import com.example.test.langlarm.persistence.PersistanceService;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {



    private Context baseContext;
    //    private TimePicker timePicker;
    private Button setAlarmButton;
    private Button addAlarmButton;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private List<Alarm> fakeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //TODO: get from db
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.SECOND, 5);
        baseContext = getBaseContext();
        fakeList = PersistanceService.loadList(baseContext, PersistanceKeys.ALARM_LIST, Alarm.class);
//        fakeList.addAll(Arrays.asList(new Alarm(14, 3, true),
//                new Alarm(11, 45, false)));



//        timePicker = (TimePicker) findViewById(R.id.timePicker);
        new GagsProvider(baseContext);
        initAlarmList();
        initSetAlarmButton();
        initAddAlarmButton();
    }

    private void initAlarmList() {
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        gridViewAdapter = new GridViewAdapter(getApplicationContext(), R.layout.alarm_list_item, fakeList);
        gridView.setAdapter(gridViewAdapter);

    }

    @Override
    public void onItemClick(final AdapterView<?> arg0, final View view, final int position, final long id) {
        String message = "Clicked : " + fakeList.get(position).getFormattedTime();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initAddAlarmButton() {
        addAlarmButton = (Button) findViewById(R.id.addAlarmButton);
        final Activity mainActivity = this;
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, R.style.AppTheme_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fakeList.add(new Alarm(selectedHour, selectedMinute, true));
                        PersistanceService.saveList(mainActivity, PersistanceKeys.ALARM_LIST, fakeList);
                        gridView.invalidateViews();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private void initSetAlarmButton() {
        setAlarmButton = (Button) findViewById(R.id.setAlarmButton);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                int hour = timePicker.getCurrentHour();
//                int min = timePicker.getCurrentMinute();
                //TODO set time and refactor
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND, 5);

//                AlarmService.startAlarm(getBaseContext(), new Alarm(calendar.getTimeInMillis(), true));
                Intent intent = new Intent(getBaseContext(), WakeUpActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager am =  (AlarmManager) getBaseContext().getSystemService(Activity.ALARM_SERVICE);
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                        pendingIntent);


                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(String.format("Alert will start at %s:%s", 10, 22));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }
}
