package fi.ukkosnetti.symprap;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import fi.ukkosnetti.symprap.dto.User;
import fi.ukkosnetti.symprap.dto.UserRole;
import fi.ukkosnetti.symprap.util.CurrentUser;

public class ReportsUserSelectionActivity extends Activity {

    protected @Bind(R.id.followeeList) ListView followees;

    private List<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_user_selection);
        ButterKnife.bind(this);
        usernames = new ArrayList<>();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        addUsernamesToList();
        followees.setAdapter(new ArrayAdapter<>(this, R.layout.followee_list_item, usernames));
    }

    @OnItemClick(R.id.followeeList)
    public void toMainReports(int position) {
        toMainReports(usernames.get(position));
    }

    private void addUsernamesToList() {
        User user = CurrentUser.getCurrentUser();
        if (user.roles.contains(UserRole.TEEN)) usernames.add(user.userName);
        usernames.addAll(user.followees);
    }

    private void toMainReports(String username) {
        Intent intent = new Intent(this, ReportsMainActivity.class);
        intent.putExtra(ReportsMainActivity.USERNAME_KEY, username);
        startActivity(intent);
    }
}
