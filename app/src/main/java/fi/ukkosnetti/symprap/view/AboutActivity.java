package fi.ukkosnetti.symprap.view;

import android.os.Bundle;
import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.ukkosnetti.symprap.R;

public class AboutActivity extends SymprapActivity {

    protected @Bind(R.id.aboutText) TextView aboutText;
    protected Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_about);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) aboutText.startAnimation(animation);
    }
}
