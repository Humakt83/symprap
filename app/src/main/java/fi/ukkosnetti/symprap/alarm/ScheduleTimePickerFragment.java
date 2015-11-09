package fi.ukkosnetti.symprap.alarm;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * UI component that provides a TimePicker
 */
public class ScheduleTimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private OnCompleteListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnCompleteListener) activity;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        listener.onComplete(cal.getTime());
        this.dismiss();
    }

    public interface OnCompleteListener {

        void onComplete(Date time);

    }
}
