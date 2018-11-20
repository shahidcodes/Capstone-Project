package ml.shahidkamal.flatmatestaskreminder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ml.shahidkamal.flatmatestaskreminder.AddTaskActivity;
import ml.shahidkamal.flatmatestaskreminder.R;
import ml.shahidkamal.flatmatestaskreminder.model.Task;
import ml.shahidkamal.flatmatestaskreminder.utils.Constants;

public class AdapterTaskList extends RecyclerView.Adapter<AdapterTaskList.TaskListViewHolder> {

    Context context;
    private LayoutInflater inflater;
    private List<Task> tasks;

    public AdapterTaskList(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_task_row, viewGroup, false);
        return new TaskListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder taskListViewHolder, int i) {
        final Task task = tasks.get(i);
        taskListViewHolder.taskName.setText(task.getName());
        taskListViewHolder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddTaskActivity.class);
                intent.putExtra(Constants.INTENT_KEY_JOB_OBJECT, task);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tasks != null) return tasks.size();
        else return 0;

    }

    class TaskListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_task_name)
        TextView taskName;
        @BindView(R.id.rv_item_root_layout)
        LinearLayout rootLayout;

        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }
}
