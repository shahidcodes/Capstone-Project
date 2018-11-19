package ml.shahidkamal.flatmatestaskreminder.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ml.shahidkamal.flatmatestaskreminder.model.Task;

@Database(entities = {Task.class}, version = 3)
public abstract class TaskRoomDatabase extends RoomDatabase {

    private static final String DB_NAME = "TaskRoomDatabase";
    private static volatile TaskRoomDatabase INSTANCE;

    public abstract TaskDao taskDao();

    public static TaskRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TaskRoomDatabase.class,
                            DB_NAME
                    ).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
