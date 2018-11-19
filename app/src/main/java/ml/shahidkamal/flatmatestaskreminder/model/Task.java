package ml.shahidkamal.flatmatestaskreminder.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Random;

@Entity(tableName = "task_table")
public class Task implements Serializable {

    private String name;
    private String description;
    private boolean isRecurring;
    private String recurringDay;
    @PrimaryKey
    @NonNull
    private int taskId;

    public Task(){
        Random random = new Random();
        taskId = random.nextInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public String getRecurringDay() {
        return recurringDay;
    }

    public void setRecurringDay(String recurringDay) {
        this.recurringDay = recurringDay;
    }

    @NonNull
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(@NonNull int taskId) {
        this.taskId = taskId;
    }
}
