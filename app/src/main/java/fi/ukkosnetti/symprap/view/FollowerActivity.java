package fi.ukkosnetti.symprap.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.base.Strings;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import fi.ukkosnetti.symprap.R;
import fi.ukkosnetti.symprap.proxy.SymprapConnector;
import fi.ukkosnetti.symprap.util.CurrentUser;

public class FollowerActivity extends Activity {

    protected @Bind(R.id.followersList) ListView followersList;
    protected @Bind(R.id.followerEditText) EditText followerEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        ButterKnife.bind(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        followersList.setAdapter(new ArrayAdapter<>(this, R.layout.follower_list_item, CurrentUser.getCurrentUser().followers));
    }

    @OnClick(R.id.addFollowerButton)
    public void addFollower() {
        final String follower = followerEdit.getText().toString();
        if (validateFollower(follower)) {
            new AddFollowerTask(follower).execute();
        }
    }

    @OnItemClick(R.id.followersList)
    public void removeFollower(int position) {
        String follower = CurrentUser.getCurrentUser().followers.get(position);
        new RemoveFollowerTask(follower).execute();
    }

    private boolean validateFollower(String follower) {
        if (Strings.isNullOrEmpty(follower)) {
            Toast.makeText(this, "Username of follower not provided", Toast.LENGTH_LONG).show();
            return false;
        } else if (CurrentUser.getCurrentUser().followers.contains(follower)) {
            Toast.makeText(this, "Follower of that name has already been added", Toast.LENGTH_LONG).show();
            return false;
        } else if (CurrentUser.getCurrentUser().userName.equals(follower)) {
            Toast.makeText(this, "Cannot add yourself as follower", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private class AddFollowerTask extends AsyncTask<Void, Void, Boolean> {

        private final String follower;

        public AddFollowerTask(String follower) {
            this.follower = follower;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return SymprapConnector.proxy(FollowerActivity.this).addFollower(follower).getStatus() == 200;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                CurrentUser.getCurrentUser().followers.add(follower);
                ((ArrayAdapter)followersList.getAdapter()).notifyDataSetChanged();
            } else {
                Toast.makeText(FollowerActivity.this, "Adding follower failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class RemoveFollowerTask extends AsyncTask<Void, Void, Boolean> {

        private final String follower;

        public RemoveFollowerTask(String follower) {
            this.follower = follower;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return SymprapConnector.proxy(FollowerActivity.this).removeFollower(follower).getStatus() == 200;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                CurrentUser.getCurrentUser().followers.remove(follower);
                ((ArrayAdapter)followersList.getAdapter()).notifyDataSetChanged();
            } else {
                Toast.makeText(FollowerActivity.this, "Removing follower failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}
