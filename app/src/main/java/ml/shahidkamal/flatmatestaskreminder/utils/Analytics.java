package ml.shahidkamal.flatmatestaskreminder.utils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {
    public static final String EVENT_MESSAGE = "EVENT_MESSAGE";
    public static final String EVENT_WINDOW_START = "EVENT_WINDOW_START_VALUE";
    private static FirebaseAnalytics mFirebaseAnalytics;
    public static String EVENT_ERROR = "EVENT_ERROR";

    public static void register(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }


    public static void logEvent(String tag, Bundle bundle){
        if(mFirebaseAnalytics!= null) mFirebaseAnalytics.logEvent(tag, bundle);
    }
}
