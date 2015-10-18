package fi.ukkosnetti.symprap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.ukkosnetti.symprap.dto.Answer;
import fi.ukkosnetti.symprap.dto.Question;
import fi.ukkosnetti.symprap.util.CurrentUser;
import fi.ukkosnetti.symprap.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class QuestionsActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    public static final String QUESTIONS_KEY = "questions";

    private Iterator<Question> questions;

    private List<Answer> answers;

    private Question currentQuestion;

    protected @Bind(R.id.question) TextView questionView;

    protected @Bind(R.id.abort_button) Button abortButton;

    protected @Bind(R.id.numberAnswer) EditText numberField;

    protected @Bind(R.id.textAnswer) EditText textField;

    protected @Bind(R.id.boolean_answers) View booleanAnswers;

    protected @Bind(R.id.number_answers) View numberAnswers;

    protected @Bind(R.id.text_answers) View textAnswers;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.question);
        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        abortButton.setOnTouchListener(mDelayHideTouchListener);
        answers = new ArrayList<>();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
        questions = ((List<Question>)getIntent().getSerializableExtra(QuestionsActivity.QUESTIONS_KEY)).iterator();
        setupQuestion();
    }

    private void setupQuestion() {
        currentQuestion = questions.next();
        questionView.setText(currentQuestion.question);
        textAnswers.setVisibility(View.INVISIBLE);
        booleanAnswers.setVisibility(View.INVISIBLE);
        numberAnswers.setVisibility(View.INVISIBLE);
        switch(currentQuestion.answerType) {
            case BOOLEAN:
                booleanAnswers.setVisibility(View.VISIBLE);
                break;
            case TEXT:
                textAnswers.setVisibility(View.VISIBLE);
                break;
            case DOUBLE:
                numberAnswers.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.number_button)
    public void answerNumber() {
        continueQuestions(numberField.getText().toString());
    }

    @OnClick(R.id.text_button)
    public void answerText() {
        continueQuestions(textField.getText().toString());
    }

    @OnClick(R.id.yes_button)
    public void answerYes() {
        continueQuestions(Boolean.TRUE.toString());
    }

    @OnClick(R.id.no_button)
    public void answerNo() {
        continueQuestions(Boolean.FALSE.toString());
    }

    @OnClick(R.id.abort_button)
    public void abort() {
        startActivity(new Intent(QuestionsActivity.this, MainActivity.class));
    }

    private void continueQuestions(String answer) {
        answers.add(new Answer(currentQuestion.id, CurrentUser.getCurrentUser().id, answer));
        if (questions.hasNext()) {
            setupQuestion();
        } else {

        }
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
