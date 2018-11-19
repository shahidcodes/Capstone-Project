package ml.shahidkamal.flatmatestaskreminder.scheduler;

import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import ml.shahidkamal.flatmatestaskreminder.Constants;
import ml.shahidkamal.flatmatestaskreminder.R;
import ml.shahidkamal.flatmatestaskreminder.TaskListActivity;
import ml.shahidkamal.flatmatestaskreminder.model.Task;

import static ml.shahidkamal.flatmatestaskreminder.Constants.CHANNEL_ID;
import static ml.shahidkamal.flatmatestaskreminder.Constants.INTENT_KEY_JOB_OBJECT;

public class NotificationService extends JobService {
    private static final String TAG = "NotificationService";

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "onStartJob");
        try {
            Task task = (Task) job.getExtras().getSerializable(INTENT_KEY_JOB_OBJECT);
            String name = task.getName();
            String desc = task.getDescription();
            int taskId = task.getTaskId();

            Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
            intent.putExtra(INTENT_KEY_JOB_OBJECT, task);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle(name)
                    .setContentText(desc)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_small_notification)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            notificationManager.notify(taskId, mBuilder.build());
        }catch (NullPointerException ex){
            Log.e(TAG, "onStartJob: NullPointer", ex);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d(TAG, "onStopJob");
        return false;
    }
}
