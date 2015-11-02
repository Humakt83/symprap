package fi.ukkosnetti.symprap.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomTimeBasedAlarmManager {

    private Context context;
    private static final String ALARM_TIMES_KEY = "symprap_alarm_times";

    public CustomTimeBasedAlarmManager(Context context) {
        this.context = context;
    }

    public void saveAlarms(Intent intent, List<Date> dates) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        cancelAlarms(intent, alarmManager);
        setAlarms(intent, alarmManager, dates);
    }

    public List<Date> getSavedAlarms() {
        List<Date> alarmDates = new ArrayList<>();
        Set<String> savedAlarms = getSavedAlarmIdentifiers();
        for (String alarmIdentifier : savedAlarms) {
            alarmDates.add(new Date(Long.parseLong(alarmIdentifier)));
        }
        Collections.sort(alarmDates);
        return alarmDates;
    }

    private void cancelAlarms(Intent intent, AlarmManager alarmManager) {
        Set<String> savedAlarms = getSavedAlarmIdentifiers();
        for (String alarmIdentifier : savedAlarms) {
            alarmManager.cancel(PendingIntent.getBroadcast(context, (int)Long.parseLong(alarmIdentifier), intent, 0));
        }
    }

    private void setAlarms(Intent intent, AlarmManager alarmManager, List<Date> dates) {
        Set<String> alarmTimes = new HashSet<>();
        for (Date date : dates) {
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, (int)date.getTime(), intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(), AlarmManager.INTERVAL_DAY, alarmIntent);
            alarmTimes.add("" + date.getTime());
        }
        PreferenceManager.getDefaultSharedPreferences(context).edit().putStringSet(ALARM_TIMES_KEY, alarmTimes).apply();
    }

    @NonNull
    private Set<String> getSavedAlarmIdentifiers() {
        return PreferenceManager.getDefaultSharedPreferences(context).getStringSet(ALARM_TIMES_KEY, new HashSet<String>());
    }

}
