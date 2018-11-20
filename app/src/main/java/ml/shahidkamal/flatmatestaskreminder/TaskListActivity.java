package ml.shahidkamal.flatmatestaskreminder;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;

import butterknife.BindString;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.fabric.sdk.android.Fabric;
import ml.shahidkamal.flatmatestaskreminder.scheduler.TaskScheduler;
import ml.shahidkamal.flatmatestaskreminder.utils.Analytics;

import static ml.shahidkamal.flatmatestaskreminder.utils.Constants.CHANNEL_DESC;
import static ml.shahidkamal.flatmatestaskreminder.utils.Constants.CHANNEL_ID;
import static ml.shahidkamal.flatmatestaskreminder.utils.Constants.CHANNEL_NAME;

public class TaskListActivity extends AppCompatActivity {

    @BindString(R.string.clear_reminder_success_msg) String clearReminderSuccessMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createNotificationChannel();
        Analytics.register(this);
        Bundle bundle = new Bundle();
        bundle.putString("APP_START", "APP STARTED");
        Analytics.logEvent(Analytics.EVENT_MESSAGE, bundle);
        Fabric.with(this, new Crashlytics());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESC);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_clear_all_reminders){
            clearAllReminders();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearAllReminders() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.confirm_clear_reminder_title)
                .setMessage(R.string.confirm_clear_reminder_message)
                .setPositiveButton(R.string.confirm_clear_reminder_pos_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaskScheduler scheduler = new TaskScheduler(TaskListActivity.this);
                        scheduler.cancelAllSchedule();
                        Toasty.success(TaskListActivity.this, clearReminderSuccessMsg, Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton(R.string.confirm_clear_reminder_neg_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
