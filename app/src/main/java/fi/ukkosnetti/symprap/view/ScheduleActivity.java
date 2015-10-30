package fi.ukkosnetti.symprap.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.ukkosnetti.symprap.R;
import fi.ukkosnetti.symprap.alarm.CustomTimeBasedAlarmManager;
import fi.ukkosnetti.symprap.receiver.QuestionsNotificationReceiver;

public class ScheduleActivity extends SymprapActivity {

    protected @Bind(R.id.remindersList) ListView remindersList;
    protected BaseAdapter reminderAdapter;
    private List<Date> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        dates = new ArrayList<>();
        reminderAdapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return dates.size();
            }

            @Override
            public Object getItem(int position) {
                return dates.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.schedule_list_item, null, false);
                }
                TimePicker timePicker = (TimePicker) convertView.findViewById(R.id.scheduleTimePicker);
                Date date = dates.get(position);
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);
                timePicker.setIs24HourView(true);
                timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                timePicker.setMinute(calendar.get(Calendar.MINUTE));
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        Date updateDate = dates.get(position);
                        Calendar cal = GregorianCalendar.getInstance();
                        cal.setTime(updateDate);
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        updateDate.setTime(cal.getTimeInMillis());
                    }
                });
                Button removeButton = (Button)convertView.findViewById(R.id.removeReminderButton);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dates.remove(position);
                        reminderAdapter.notifyDataSetChanged();
                    }
                });
                return convertView;
            }
        };
        remindersList.setAdapter(reminderAdapter);
    }

    @OnClick(R.id.addReminderButton)
    public void addReminder() {
        dates.add(new Date());
        reminderAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.saveRemindersButton)
    public void saveAlarms() {
        Intent intent = new Intent(this, QuestionsNotificationReceiver.class);
        new CustomTimeBasedAlarmManager(this).saveAlarms(intent, dates);
        Toast.makeText(this, "Notification times for check-ins updated", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }


}
