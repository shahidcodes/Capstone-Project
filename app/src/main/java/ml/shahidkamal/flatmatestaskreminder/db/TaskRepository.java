package ml.shahidkamal.flatmatestaskreminder.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ml.shahidkamal.flatmatestaskreminder.model.Task;

public class TaskRepository {
    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;

    public TaskRepository(Application application) {
        TaskRoomDatabase taskRoomDatabase = TaskRoomDatabase.getDatabase(application);
        mTaskDao = taskRoomDatabase.taskDao();
        mAllTasks= mTaskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public void insert(Task task){
        new insertAsyncTask(mTaskDao).execute(task);
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        public insertAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }
}
