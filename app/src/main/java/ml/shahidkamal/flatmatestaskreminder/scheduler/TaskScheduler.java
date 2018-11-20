package ml.shahidkamal.flatmatestaskreminder.scheduler;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.Calendar;

import ml.shahidkamal.flatmatestaskreminder.model.Task;
import ml.shahidkamal.flatmatestaskreminder.utils.Analytics;
import ml.shahidkamal.flatmatestaskreminder.utils.Constants;

public class TaskScheduler {
    private static final String TAG = "TaskScheduler";

    FirebaseJobDispatcher dispatcher;
    Context context;

    public TaskScheduler(Context context) {
        this.context = context;
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }

    public void scheduleTask(Task task) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_KEY_JOB_NAME, task.getName());
        bundle.putString(Constants.INTENT_KEY_JOB_DESC, task.getDescription());
        bundle.putString(Constants.INTENT_KEY_JOB_RECUR_DAY, task.getRecurringDay());
        bundle.putBoolean(Constants.INTENT_KEY_JOB_RECURRING, task.isRecurring());
        bundle.putInt(Constants.INTENT_KEY_JOB_ID, task.getTaskId());

        int windowStart = getStartWindow(task.getRecurringDay());
        Bundle bundle1 = new Bundle();
        bundle1.putInt("windowStart=", windowStart);
        Analytics.logEvent(Analytics.EVENT_WINDOW_START, bundle1);
        // schedule task to next task.getRecurringDay()
        schedule(task.getTaskId(), bundle, windowStart, false);
    }


    public void schedule(int taskId, Bundle bundle, int windowStart, boolean recurring){
        Log.d(TAG, "schedule: windowStart" + windowStart);
        Job job = dispatcher.newJobBuilder()
                .setService(NotificationService.class)
                .setTag(String.valueOf(taskId))
                .setTrigger(Trigger.executionWindow(windowStart, windowStart + 3600))
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(recurring)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setExtras(bundle)
                .build();
        dispatcher.mustSchedule(job);
    }


    private int getStartWindow(String recurringDay) {
        int dayOfWeek = getDayOfWeek(recurringDay);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int diff = Math.abs(dayOfWeek- day);
        return diff * 3600;
    }

    private int getDayOfWeek(String recurringDay) {
        switch (recurringDay) {
            case "FRIDAY":
                return Calendar.FRIDAY;
            case "SATURDAY":
                return Calendar.SATURDAY;
            case "SUNDAY":
                return Calendar.SUNDAY;
            case "MONDAY":
                return Calendar.MONDAY;
            case "TUESDAY":
                return Calendar.TUESDAY;
            case "WEDNESDAY":
                return Calendar.WEDNESDAY;
            case "THURSDAY":
                return Calendar.THURSDAY;
            default:
                return Calendar.FRIDAY;
        }
    }

    public void cancelSchedule(Task task) {
        dispatcher.cancel(String.valueOf(task.getTaskId()));
    }
}
