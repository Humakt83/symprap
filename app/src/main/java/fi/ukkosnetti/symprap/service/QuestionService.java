package fi.ukkosnetti.symprap.service;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;

import fi.ukkosnetti.symprap.view.QuestionsActivity;
import fi.ukkosnetti.symprap.dto.Disease;
import fi.ukkosnetti.symprap.dto.Question;
import fi.ukkosnetti.symprap.proxy.SymprapConnector;
import fi.ukkosnetti.symprap.proxy.SymprapProxy;
import fi.ukkosnetti.symprap.util.CurrentUser;

public class QuestionService extends IntentService {

    public QuestionService() {
        super(QuestionService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SymprapProxy proxy = SymprapConnector.proxy(this);
        ArrayList<Question> questions = new ArrayList<>();
        for (Disease disease : CurrentUser.getCurrentUser().diseases) {
            questions.addAll(proxy.getQuestionsForDisease(disease.id));
        }
        Intent questionsIntent = new Intent(this, QuestionsActivity.class);
        questionsIntent.putExtra(QuestionsActivity.QUESTIONS_KEY, questions);
        questionsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(questionsIntent);
    }
}
