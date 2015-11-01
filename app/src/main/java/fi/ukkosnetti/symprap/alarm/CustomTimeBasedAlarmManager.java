package fi.ukkosnetti.symprap.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import java.util.Date;
import java.util.List;

public class CustomTimeBasedAlarmManager {

    private Context context;

    public CustomTimeBasedAlarmManager(Context context) {
        this.context = context;
    }

    public void saveAlarms(Intent intent, List<Date> dates) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        cancelAlarms(intent, alarmManager);
        setAlarms(intent, alarmManager, dates);
    }

    private void cancelAlarms(Intent intent, AlarmManager alarmManager) {
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.cancel(alarmIntent);
    }

    private void setAlarms(Intent intent, AlarmManager alarmManager, List<Date> dates) {
        for (Date date : dates) {
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, (int)date.getTime(), intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(), AlarmManager.INTERVAL_DAY, alarmIntent);
        }
    }

}
