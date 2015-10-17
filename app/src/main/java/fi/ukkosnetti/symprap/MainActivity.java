package fi.ukkosnetti.symprap;

import android.app.Activity;
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
import fi.ukkosnetti.symprap.proxy.SymprapConnector;
import fi.ukkosnetti.symprap.proxy.SymprapProxy;
import fi.ukkosnetti.symprap.util.CurrentUser;

public class MainActivity extends Activity {

    protected @Bind(R.id.questionButton) ImageButton questionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        questionButton.setVisibility(CurrentUser.getCurrentUser().diseases.isEmpty() ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @OnClick(R.id.questionButton)
    public void toQuestions() {
        new AsyncTask<SymprapProxy, Void, ArrayList<Question>>() {

            @Override
            protected ArrayList<Question> doInBackground(SymprapProxy... params) {
                Long diseaseId = CurrentUser.getCurrentUser().diseases.get(0).id;
                return (ArrayList<Question>)params[0].getQuestionsForDisease(diseaseId);
            }

            @Override
            protected void onPostExecute(ArrayList<Question> questions) {
                super.onPostExecute(questions);
                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                intent.putExtra(QuestionsActivity.QUESTIONS_KEY, questions);
                startActivity(intent);
            }
        }.execute(SymprapConnector.proxy(getApplicationContext()));

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