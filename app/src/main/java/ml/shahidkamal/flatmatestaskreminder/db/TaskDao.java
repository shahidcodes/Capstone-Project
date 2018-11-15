package ml.shahidkamal.flatmatestaskreminder.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ml.shahidkamal.flatmatestaskreminder.model.Task;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks();
}
