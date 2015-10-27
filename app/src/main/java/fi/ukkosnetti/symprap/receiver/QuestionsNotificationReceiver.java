package fi.ukkosnetti.symprap.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import fi.ukkosnetti.symprap.R;
import fi.ukkosnetti.symprap.service.QuestionService;

public class QuestionsNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent questionsIntent = PendingIntent.getService(
                context,
                0,
                new Intent(context, QuestionService.class),
                PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setContentTitle("Questions time!")
                .setContentIntent(questionsIntent)
                .setAutoCancel(true)
                .build();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);
    }
}
