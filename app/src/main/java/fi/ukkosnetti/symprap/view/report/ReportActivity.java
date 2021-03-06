package fi.ukkosnetti.symprap.view.report;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import fi.ukkosnetti.symprap.R;
import fi.ukkosnetti.symprap.dto.AnswerGet;
import fi.ukkosnetti.symprap.util.Constants;
import fi.ukkosnetti.symprap.view.SymprapActivity;

public abstract class ReportActivity extends SymprapActivity {

    public final static String QUESTION_KEY = "report_question_key";
    public final static String ANSWERS_KEY = "report_answers_key";

    protected @Bind(R.id.questionTitleReport) TextView questionTitle;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        List<AnswerGet> answers = (List<AnswerGet>) getIntent().getSerializableExtra(ANSWERS_KEY);
        String question = getIntent().getStringExtra(QUESTION_KEY);
        questionTitle.setText(question);
        renderAnswers((answers));
    }

    protected abstract void renderAnswers(List<AnswerGet> answers);

    protected String formatAnswerCreationDate(AnswerGet answer) {
        return Constants.DATETIME_FORMATTER.format(new Date(answer.created));
    }
}
