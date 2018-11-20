package ml.shahidkamal.flatmatestaskreminder.db;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ml.shahidkamal.flatmatestaskreminder.model.Task;

public class TaskRepository {
    private static final String TAG = "TaskRepository";
    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;

    public TaskRepository(Context context) {
        TaskRoomDatabase taskRoomDatabase = TaskRoomDatabase.getDatabase(context);
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

    public void update(Task task){
        new updateAsyncTask(mTaskDao).execute(task);
    }

    private static class updateAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        public updateAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            Log.d(TAG, "doInBackground: " + tasks[0].getTaskId());
            taskDao.update(tasks[0]);
            return null;
        }
    }


    public void delete(Task task){
        new deleteAsyncTask(mTaskDao).execute(task);
    }

    private static class deleteAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        public deleteAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }

}


