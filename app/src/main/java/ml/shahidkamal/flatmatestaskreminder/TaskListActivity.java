package ml.shahidkamal.flatmatestaskreminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ml.shahidkamal.flatmatestaskreminder.utils.Analytics;

import static ml.shahidkamal.flatmatestaskreminder.utils.Constants.CHANNEL_DESC;
import static ml.shahidkamal.flatmatestaskreminder.utils.Constants.CHANNEL_ID;
import static ml.shahidkamal.flatmatestaskreminder.utils.Constants.CHANNEL_NAME;

public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        Analytics.register(this);
        Bundle bundle = new Bundle();
        bundle.putString("APP_START", "APP STARTED");
        Analytics.logEvent(Analytics.EVENT_MESSAGE, bundle);
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
}
