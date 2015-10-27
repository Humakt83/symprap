package fi.ukkosnetti.symprap;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.ukkosnetti.symprap.dto.Question;
import fi.ukkosnetti.symprap.dto.User;
import fi.ukkosnetti.symprap.dto.UserRole;
import fi.ukkosnetti.symprap.proxy.SymprapConnector;
import fi.ukkosnetti.symprap.proxy.SymprapProxy;
import fi.ukkosnetti.symprap.receiver.QuestionsNotificationReceiver;
import fi.ukkosnetti.symprap.task.QuestionsTask;
import fi.ukkosnetti.symprap.util.CurrentUser;

public class MainActivity extends Activity {

    protected @Bind(R.id.questionButton) ImageButton questionButton;

    private final static Integer ALARM_INTERVAL_TWO_MINUTES = 30000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        User currentUser = CurrentUser.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        questionButton.setVisibility(currentUser.diseases.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        if (currentUser.roles.contains(UserRole.TEEN)) {
            createAlarm();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @OnClick(R.id.questionButton)
    public void toQuestions() {
        new QuestionsTask(this).execute(SymprapConnector.proxy(this));
    }

    @OnClick(R.id.statisticsButton)
    public void toStatistics() {
        startActivity(new Intent(this, ReportsMainActivity.class));
    }

    @OnClick(R.id.settingsButton)
    public void toSettings() {
        startActivity(new Intent(this, ScheduleActivity.class));
    }

    private void createAlarm() {
        Intent intent = new Intent(this, QuestionsNotificationReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Service.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                ALARM_INTERVAL_TWO_MINUTES, ALARM_INTERVAL_TWO_MINUTES, alarmIntent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            CurrentUser.setCurrentUser(null);
            startActivity(new Intent(
                    MainActivity.this,
                    LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
