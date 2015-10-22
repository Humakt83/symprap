package fi.ukkosnetti.symprap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.ukkosnetti.symprap.dto.Question;
import fi.ukkosnetti.symprap.proxy.SymprapConnector;
import fi.ukkosnetti.symprap.proxy.SymprapProxy;
import fi.ukkosnetti.symprap.util.CurrentUser;

public class ReportsMainActivity extends Activity {

    protected @Bind(R.id.questionsList) ListView questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new AsyncTask<SymprapProxy, Void, ArrayList<Question>>() {

            @Override
            protected ArrayList<Question> doInBackground(SymprapProxy... params) {
                Long diseaseId = CurrentUser.getCurrentUser().diseases.get(0).id;
                return (ArrayList<Question>)params[0].getQuestionsForDisease(diseaseId);
            }

            @Override
            protected void onPostExecute(ArrayList<Question> questions) {
                questionsList.setAdapter(new ArrayAdapter<Question>(ReportsMainActivity.this,
                        R.layout.question_list_item, questions));
            }

        }.execute(SymprapConnector.proxy(getApplicationContext()));
    }
}
