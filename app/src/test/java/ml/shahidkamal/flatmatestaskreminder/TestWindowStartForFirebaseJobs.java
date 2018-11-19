package ml.shahidkamal.flatmatestaskreminder;

import android.annotation.SuppressLint;

import org.junit.Test;

import java.util.Calendar;

public class TestWindowStartForFirebaseJobs {
    private int getStartWindow(String recurringDay) {
        int dayOfWeek = getDayOfWeek(recurringDay);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int diff = Math.abs(dayOfWeek- day);
        return diff * 3600;
    }

    @SuppressLint("NewApi")
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
    @Test
    public void ifDiffIsCorrect(){
        String[] days = { "SUNDAY", "MONDAY", "TUESDAY" };
        for (String day: days) {
            int window = getStartWindow(day);
            System.out.println("DAY " + day + ", DIFF: " + window);
        }
    }
}
