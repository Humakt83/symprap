package fi.ukkosnetti.symprap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import fi.ukkosnetti.symprap.dto.AnswerGet;
import fi.ukkosnetti.symprap.dto.Question;
import fi.ukkosnetti.symprap.proxy.SymprapConnector;
import fi.ukkosnetti.symprap.proxy.SymprapProxy;
import fi.ukkosnetti.symprap.util.CurrentUser;

public class ReportsMainActivity extends Activity {

    protected @Bind(R.id.questionsList) ListView questionsList;

    private List<AnswerGet> answersToQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_main);
        ButterKnife.bind(this);
        answersToQuestions = new ArrayList<>();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SymprapProxy proxy = SymprapConnector.proxy(getApplicationContext());
        getAnswers(proxy);
        getQuestions(proxy);
    }

    @OnItemClick(R.id.questionsList)
    protected void toAnswers(int position) {
        Question question = (Question) questionsList.getAdapter().getItem(position);
        boolean found = false;
        for (AnswerGet answer : answersToQuestions) {
            found = found || answer.questionId.equals(question.id);
        }
        if (!found) {
            Toast.makeText(getApplicationContext(), "No answer data available", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAnswers(SymprapProxy proxy) {
        new AsyncTask<SymprapProxy, Void, List<AnswerGet>>() {

            @Override
            protected List<AnswerGet> doInBackground(SymprapProxy... params) {
                return params[0].getAnswersForUser(CurrentUser.getCurrentUser().userName);
            }

            @Override
            protected void onPostExecute(List<AnswerGet> answers) {
                answersToQuestions = answers;
            }

        }.execute(proxy);
    }

    private void getQuestions(SymprapProxy proxy) {
        new AsyncTask<SymprapProxy, Void, List<Question>>() {

            @Override
            protected List<Question> doInBackground(SymprapProxy... params) {
                Long diseaseId = CurrentUser.getCurrentUser().diseases.get(0).id;
                return params[0].getQuestionsForDisease(diseaseId);
            }

            @Override
            protected void onPostExecute(List<Question> questions) {
                questionsList.setAdapter(new ArrayAdapter<Question>(ReportsMainActivity.this,
                        R.layout.question_list_item, questions));
            }

        }.execute(proxy);
    }
}
