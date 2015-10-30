package fi.ukkosnetti.symprap.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.ukkosnetti.symprap.R;
import fi.ukkosnetti.symprap.view.report.ReportsMainActivity;
import fi.ukkosnetti.symprap.view.report.ReportsUserSelectionActivity;
import fi.ukkosnetti.symprap.dto.User;
import fi.ukkosnetti.symprap.dto.UserRole;
import fi.ukkosnetti.symprap.proxy.SymprapConnector;
import fi.ukkosnetti.symprap.task.QuestionsTask;
import fi.ukkosnetti.symprap.util.CurrentUser;

public class MainActivity extends Activity {

    protected @Bind(R.id.questionButton) ImageButton questionButton;
    protected @Bind(R.id.settingsButton) ImageButton settingsButton;
    protected @Bind(R.id.followersButton) ImageButton followersButton;

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
        boolean teenUser = currentUser.roles.contains(UserRole.TEEN);
        questionButton.setVisibility(!teenUser ? View.INVISIBLE : View.VISIBLE);
        settingsButton.setVisibility(!teenUser ? View.INVISIBLE : View.VISIBLE);
        followersButton.setVisibility(!teenUser ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @OnClick(R.id.questionButton)
    public void toQuestions() {
        new QuestionsTask(this).execute(SymprapConnector.proxy(this));
    }

    @OnClick(R.id.statisticsButton)
    public void toStatistics() {
        User user = CurrentUser.getCurrentUser();
        if (user.roles.contains(UserRole.TEEN) || !user.followees.isEmpty()) {
            toStatistics(user);
        } else {
            Toast.makeText(this, "You are not following anyone", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.settingsButton)
    public void toSettings() {
        startActivity(new Intent(this, ScheduleActivity.class));
    }

    @OnClick(R.id.followersButton)
    public void toFollowers() {
        startActivity(new Intent(this, FollowerActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            CurrentUser.setCurrentUser(null);
            startActivity(new Intent(
                    MainActivity.this,
                    LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toStatistics(User user) {
        List<String> usernames = userNamesForReports(user);
        if (usernames.size() == 1) {
            Intent intent = new Intent(this, ReportsMainActivity.class);
            intent.putExtra(ReportsMainActivity.USERNAME_KEY, usernames.get(0));
            startActivity(intent);
        } else {
            startActivity(new Intent(this, ReportsUserSelectionActivity.class));
        }
    }

    private List<String> userNamesForReports(User user) {
        List<String> userNames = new ArrayList<>();
        if (user.roles.contains(UserRole.TEEN)) userNames.add(user.userName);
        userNames.addAll(user.followees);
        return userNames;
    }
}
