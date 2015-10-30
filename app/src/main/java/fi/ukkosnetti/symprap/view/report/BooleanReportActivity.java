package fi.ukkosnetti.symprap.view.report;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.ukkosnetti.symprap.R;
import fi.ukkosnetti.symprap.dto.AnswerGet;
import fi.ukkosnetti.symprap.reports.BooleanPieChart;
import fi.ukkosnetti.symprap.util.Constants;

public class BooleanReportActivity extends ReportActivity {

    protected @Bind(R.id.booleanCanvasLayout) FrameLayout drawingLayout;
    protected @Bind(R.id.booleanReportDate) TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boolean_report);
        ButterKnife.bind(this);
    }

    @Override
    protected void renderAnswers(List<AnswerGet> answers) {
        drawingLayout.addView(createPieChart(answers));
        Collections.sort(answers);
        dateView.setText(String.format("%s - %s", formatAnswerCreationDate(answers.get(answers.size() - 1)),
            formatAnswerCreationDate(answers.get(0))));
    }

    private String formatAnswerCreationDate(AnswerGet answer) {
        return Constants.DATETIME_FORMATTER.format(new Date(answer.created));
    }

    @NonNull
    private View createPieChart(List<AnswerGet> answers) {
        Long noes = 0l;
        Long yesses = 0l;
        for (AnswerGet answer : answers) {
            if (Boolean.parseBoolean(answer.answer)) yesses++;
            else noes++;
        }
        return new BooleanPieChart(this, yesses, noes);
    }
}
