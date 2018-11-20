package ml.shahidkamal.flatmatestaskreminder.scheduler;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import ml.shahidkamal.flatmatestaskreminder.R;
import ml.shahidkamal.flatmatestaskreminder.TaskListActivity;
import ml.shahidkamal.flatmatestaskreminder.model.Task;
import ml.shahidkamal.flatmatestaskreminder.utils.Analytics;
import ml.shahidkamal.flatmatestaskreminder.utils.Constants;

import static ml.shahidkamal.flatmatestaskreminder.utils.Constants.CHANNEL_ID;
import static ml.shahidkamal.flatmatestaskreminder.utils.Constants.INTENT_KEY_JOB_OBJECT;

public class NotificationService extends JobService {
    private static final String TAG = "NotificationService";

    @Override
    public boolean onStartJob(JobParameters job) {
        try {
            String name = job.getExtras().getString(Constants.INTENT_KEY_JOB_NAME);
            String desc = job.getExtras().getString(Constants.INTENT_KEY_JOB_DESC);
            String recurDay = job.getExtras().getString(Constants.INTENT_KEY_JOB_RECUR_DAY);
            boolean isRecurring = job.getExtras().getBoolean(Constants.INTENT_KEY_JOB_RECURRING);
            int taskId = job.getExtras().getInt(Constants.INTENT_KEY_JOB_ID);

            Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
            Task task = new Task();
            task.setRecurring(isRecurring);
            task.setName(name);
            task.setDescription(desc);
            task.setRecurringDay(recurDay);
            intent.putExtra(INTENT_KEY_JOB_OBJECT, task);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle(name)
                    .setContentText(desc)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_small_notification)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            notificationManager.notify(taskId, mBuilder.build());

            TaskScheduler scheduler = new TaskScheduler(getApplicationContext());
            scheduler.schedule(taskId, job.getExtras(), 7 * 24 * 60 * 60, true);
            Analytics.logEvent(Analytics.EVENT_MESSAGE, job.getExtras());
        }catch (Exception ex){
            Log.e(TAG, "onStartJob: NullPointer", ex);
            Crashlytics.log(Log.ERROR, TAG, "NPE NOTIFICATION");
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d(TAG, "onStopJob");
        return false;
    }
}
