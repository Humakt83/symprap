package fi.ukkosnetti.symprap.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;

import fi.ukkosnetti.symprap.QuestionsActivity;
import fi.ukkosnetti.symprap.dto.Disease;
import fi.ukkosnetti.symprap.dto.Question;
import fi.ukkosnetti.symprap.proxy.SymprapProxy;
import fi.ukkosnetti.symprap.util.CurrentUser;

public class QuestionsTask extends AsyncTask<SymprapProxy, Void, ArrayList<Question>> {

    private final Context context;

    public QuestionsTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Question> doInBackground(SymprapProxy... params) {
        ArrayList<Question> questions = new ArrayList<>();
        for (Disease disease : CurrentUser.getCurrentUser().diseases) {
            questions.addAll(params[0].getQuestionsForDisease(disease.id));
        }
        return questions;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        Intent intent = new Intent(context, QuestionsActivity.class);
        intent.putExtra(QuestionsActivity.QUESTIONS_KEY, questions);
        context.startActivity(intent);
    }
}