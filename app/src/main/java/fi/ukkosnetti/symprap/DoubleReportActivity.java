package fi.ukkosnetti.symprap;

import android.os.Bundle;
import android.app.Activity;

import java.util.List;

import butterknife.ButterKnife;
import fi.ukkosnetti.symprap.dto.AnswerGet;

public class DoubleReportActivity extends ReportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_report);
        ButterKnife.bind(this);
    }

    @Override
    protected void renderAnswers(List<AnswerGet> answers) {

    }
}
