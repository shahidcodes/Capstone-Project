package ml.shahidkamal.flatmatestaskreminder.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import butterknife.BindString;
import ml.shahidkamal.flatmatestaskreminder.R;
import ml.shahidkamal.flatmatestaskreminder.model.Task;
import ml.shahidkamal.flatmatestaskreminder.utils.Constants;

/**
 * Implementation of App Widget functionality.
 */
public class TaskWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        Task task = getTaskFromSharedPref(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.task_widget);
        views.setTextViewText(R.id.wtv_task_name, task.getName());
        views.setTextViewText(R.id.wtv_task_description, task.getDescription());
        views.setTextViewText(R.id.wtv_task_recur_day, task.getRecurringDay());
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static Task getTaskFromSharedPref(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Task task = new Task();
        task.setName(preferences.getString(Constants.PREF_KEY_TASK_NAME, ""));
        task.setDescription(preferences.getString(Constants.PREF_KEY_TASK_DESC, ""));
        task.setRecurringDay(preferences.getString(Constants.PREF_KEY_TASK_DAY, ""));
        task.setRecurring(preferences.getBoolean(Constants.PREF_KEY_TASK_RECURRING, false));
        task.setTaskId(preferences.getInt(Constants.PREF_KEY_TASK_ID, 1));
        return task;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

