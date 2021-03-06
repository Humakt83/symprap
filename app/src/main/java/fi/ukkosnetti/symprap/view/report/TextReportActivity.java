package fi.ukkosnetti.symprap.view.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.StackView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.ukkosnetti.symprap.R;
import fi.ukkosnetti.symprap.dto.AnswerGet;
import fi.ukkosnetti.symprap.util.Constants;

public class TextReportActivity extends ReportActivity {

    protected @Bind(R.id.textAnswerStackView) StackView textAnswerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_report);
        ButterKnife.bind(this);
    }

    @Override
    protected void renderAnswers(final List<AnswerGet> answers) {
        Collections.sort(answers);
        textAnswerView.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return answers.size();
            }

            @Override
            public Object getItem(int position) {
                return answers.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.text_answer_report_item, null, false);
                }
                TextView dateView = (TextView) convertView.findViewById(R.id.textAnswerDate);
                dateView.setText(formatAnswerCreationDate(answers.get(position)));
                TextView textView = (TextView) convertView.findViewById(R.id.textAnswerText);
                textView.setText(answers.get(position).answer);
                return convertView;
            }
        });
    }
}
