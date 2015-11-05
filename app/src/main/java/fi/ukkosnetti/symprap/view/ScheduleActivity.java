package fi.ukkosnetti.symprap.view;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.ukkosnetti.symprap.R;
import fi.ukkosnetti.symprap.alarm.CustomTimeBasedAlarmManager;
import fi.ukkosnetti.symprap.alarm.ScheduleTimePickerFragment;
import fi.ukkosnetti.symprap.receiver.QuestionsNotificationReceiver;
import fi.ukkosnetti.symprap.util.Constants;

public class ScheduleActivity extends SymprapActivity implements ScheduleTimePickerFragment.OnCompleteListener {

    protected @Bind(R.id.remindersList) ListView remindersList;
    protected BaseAdapter reminderAdapter;
    private List<Date> dates;
    private CustomTimeBasedAlarmManager alarmManager;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        alarmManager = new CustomTimeBasedAlarmManager(this);
        dates = alarmManager.getSavedAlarms();
        reminderAdapter = new AlarmTimeAdapter();
        remindersList.setAdapter(reminderAdapter);
    }

    @OnClick(R.id.addReminderButton)
    public void addReminder() {
        dates.add(Calendar.getInstance().getTime());
        reminderAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.saveRemindersButton)
    public void saveAlarms() {
        Intent intent = new Intent(this, QuestionsNotificationReceiver.class);
        alarmManager.saveAlarms(intent, dates);
        Toast.makeText(this, "Notification times for check-ins updated", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onComplete(Date time) {
        dates.set(selectedPosition, time);
        reminderAdapter.notifyDataSetChanged();
    }


    private class AlarmTimeAdapter extends BaseAdapter {

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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.schedule_list_item, null, false);
            }
            ((TextView) convertView.findViewById(R.id.timeView)).setText(Constants.TIME_FORMATTER.format(dates.get(position)));
            ((Button)convertView.findViewById(R.id.setTimeButton)).setOnClickListener(new SetScheduleOnClickListener(position));
            ((Button)convertView.findViewById(R.id.removeReminderButton)).setOnClickListener(new RemoveScheduleOnClickListener(position));
            return convertView;
        }

        private class RemoveScheduleOnClickListener implements View.OnClickListener {
            private final int position;

            public RemoveScheduleOnClickListener(int position) {
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                dates.remove(position);
                reminderAdapter.notifyDataSetChanged();
            }
        }
    }

    private class SetScheduleOnClickListener implements View.OnClickListener {

        private final int position;

        public SetScheduleOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            ScheduleActivity.this.selectedPosition = position;
            new ScheduleTimePickerFragment().show(getFragmentManager(), "timePicker");
        }
    }
}
