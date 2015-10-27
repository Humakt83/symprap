package fi.ukkosnetti.symprap;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.ukkosnetti.symprap.util.Constants;

public class ScheduleActivity extends Activity {

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
                calendar.setTime(dates.get(position));
                timePicker.setIs24HourView(true);
                timePicker.setHour(calendar.get(Calendar.HOUR));
                timePicker.setMinute(calendar.get(Calendar.MINUTE));
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

}
