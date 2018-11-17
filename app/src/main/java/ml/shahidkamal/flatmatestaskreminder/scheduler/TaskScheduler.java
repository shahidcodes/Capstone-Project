package ml.shahidkamal.flatmatestaskreminder.scheduler;

import android.content.Context;
import android.os.Bundle;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import ml.shahidkamal.flatmatestaskreminder.Constants;
import ml.shahidkamal.flatmatestaskreminder.model.Task;

public class TaskScheduler {

    FirebaseJobDispatcher dispatcher;
    Context context;

    public TaskScheduler(Context context){
        this.context = context;
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }

    public void scheduleTask(Task task){

        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_KEY_JOB_NAME, task.getName());
        bundle.putString(Constants.INTENT_KEY_JOB_DESC, task.getDescription());
        bundle.putInt(Constants.INTENT_KEY_JOB_ID, task.getTaskId());

        Job job = dispatcher.newJobBuilder()
                .setService(NotificationService.class)
                .setTag(task.getName())
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(0, 60))
                .setLifetime(Lifetime.FOREVER)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setExtras(bundle)
                .build();
        dispatcher.mustSchedule(job);
    }
}
