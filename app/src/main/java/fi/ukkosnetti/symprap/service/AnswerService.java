package fi.ukkosnetti.symprap.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

import fi.ukkosnetti.symprap.dto.Answer;
import fi.ukkosnetti.symprap.dto.Question;
import fi.ukkosnetti.symprap.proxy.SymprapConnector;
import retrofit.client.Response;

public class AnswerService extends IntentService {

    private static final int NOTIFICATION_ID = 100;

    public static final String ACTION_ANSWER_SERVICE_RESPONSE = AnswerService.class.getName();

    public static final String ANSWERS_KEY = "answers";

    private NotificationManager notifyManager;

    private NotificationCompat.Builder mBuilder;

    public AnswerService() {
        super(AnswerService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        startNotification();

        List<Answer> answers = (List<Answer>) intent.getSerializableExtra(AnswerService.ANSWERS_KEY);
        try {
            Response response = SymprapConnector.proxy(getApplicationContext()).submitAnswers(answers);
            if (response.getStatus() == 200) {
                finishNotification("Upload succeeded");
            } else {
                finishNotification("Upload failed: " + response.getReason());
            }
        } catch (Exception e) {
            finishNotification("Uploading answers failed");
        }
    }

    private void finishNotification(String status) {
        mBuilder.setContentTitle(status)
                .setProgress(0,
                        0,
                        false)
                .setSmallIcon(android.R.drawable.stat_sys_upload_done)
                .setContentText("")
                .setTicker(status);

        notifyManager.notify(NOTIFICATION_ID,
                mBuilder.build());
    }

    private void startNotification() {
        notifyManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        mBuilder = new NotificationCompat
                .Builder(this)
                .setContentTitle("Answers Upload")
                .setContentText("Upload in progress")
                .setSmallIcon(android.R.drawable.stat_sys_upload)
                .setTicker("Uploading answers")
                .setProgress(0,
                        0,
                        true);

        notifyManager.notify(NOTIFICATION_ID,
                mBuilder.build());
    }
}
