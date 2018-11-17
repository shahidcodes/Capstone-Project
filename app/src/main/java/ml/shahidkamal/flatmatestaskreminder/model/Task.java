package ml.shahidkamal.flatmatestaskreminder.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Random;
import java.util.UUID;

@Entity(tableName = "task_table")
public class Task {

//    @PrimaryKey(autoGenerate = true)
//    public int id;
    @PrimaryKey
    @NonNull
    private String name;
    private String description;
    private String recurrence;
    private int taskId;

    public Task(){
        Random random = new Random();
        taskId = random.nextInt();
    }


    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
