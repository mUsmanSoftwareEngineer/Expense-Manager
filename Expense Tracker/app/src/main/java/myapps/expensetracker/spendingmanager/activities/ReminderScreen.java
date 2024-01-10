package myapps.expensetracker.spendingmanager.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.utilities.AlarmReceiver;

public class ReminderScreen extends AppCompatActivity {


    private Activity mActivity;
    private Context mContext;

    private int mYear, mMonth, mDay, mHour, mMinute;

    RelativeLayout reminderLayout;
    TextView reminderTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_screen);

        initVars();
        initViews();
        initFunctionality();
        initListener();

    }

    private void initVars() {
        mActivity = this;
        mContext = mActivity.getApplicationContext();
    }


    private void initViews() {

        reminderLayout=(RelativeLayout) findViewById(R.id.reminder_item);
        reminderTime=(TextView) findViewById(R.id.reminder_time);

    }

    private void initFunctionality() {


    }


    private void initListener() {

        reminderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderPopup();
            }
        });

    }

    private void reminderPopup() {

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        Toast.makeText(mActivity, minute+"", Toast.LENGTH_SHORT).show();
                            reminderTime.setText(hourOfDay+":"+minute);
                            setAlarm(hourOfDay,minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

private void setAlarm(int mHour,int mMinute)
{
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, mHour);
    calendar.set(Calendar.MINUTE, mMinute);
    calendar.set(Calendar.SECOND, 0);
    if (calendar.getTime().compareTo(new Date()) < 0) calendar.add(Calendar.HOUR_OF_DAY, 0);
    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    if (alarmManager != null) {
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}


}