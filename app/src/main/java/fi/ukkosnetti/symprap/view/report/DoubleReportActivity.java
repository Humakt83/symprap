package fi.ukkosnetti.symprap.view.report;

import android.graphics.Point;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.ukkosnetti.symprap.R;
import fi.ukkosnetti.symprap.dto.AnswerGet;
import fi.ukkosnetti.symprap.reports.SingleLineChart;
import fi.ukkosnetti.symprap.util.Constants;

public class DoubleReportActivity extends ReportActivity {

    protected @Bind(R.id.doubleReportDate) TextView dateView;
    protected @Bind(R.id.doublenCanvasLayout) FrameLayout drawingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_report);
        ButterKnife.bind(this);
    }

    @Override
    protected void renderAnswers(List<AnswerGet> answers) {
        Collections.sort(answers);
        Collections.reverse(answers);
        dateView.setText(String.format("%s - %s", formatAnswerCreationDate(answers.get(0)),
                formatAnswerCreationDate(answers.get(answers.size() - 1))));
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        drawingLayout.addView(new SingleLineChart(this, size.x, getPoints(answers)));
    }

    private List<Double> getPoints(List<AnswerGet> answers) {
        List<Double> points = new ArrayList<>();
        for (AnswerGet answer : answers) {
            points.add(Double.parseDouble(answer.answer));
        }
        return points;
    }

}
