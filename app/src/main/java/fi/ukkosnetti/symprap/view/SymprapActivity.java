package fi.ukkosnetti.symprap.view;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import fi.ukkosnetti.symprap.R;
import fi.ukkosnetti.symprap.util.CurrentUser;

public abstract class SymprapActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_main && !(this instanceof MainActivity)) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        if (id == R.id.action_settings) {
            CurrentUser.setCurrentUser(null);
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
