package ml.shahidkamal.flatmatestaskreminder.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import ml.shahidkamal.flatmatestaskreminder.AddTaskActivity;
import ml.shahidkamal.flatmatestaskreminder.R;
import ml.shahidkamal.flatmatestaskreminder.model.Task;
import ml.shahidkamal.flatmatestaskreminder.room.TaskViewModel;

public class FragmentAddTask extends Fragment {

    @BindView(R.id.etTaskName)
    EditText etTaskName;
    @BindView(R.id.etTaskDesc)
    EditText etTaskDesc;
    @BindView(R.id.btnAddTask)
    Button btnAddTask;
    private TaskViewModel taskViewModel;
    @BindString(R.string.task_name_required) String taskNameCanNotBeEmpty;
    @BindString(R.string.task_added) String taskHasBeenAdded;

    public FragmentAddTask() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fragment_add_task, container, false);
        ButterKnife.bind(this, view);
        handleAction();
        return view;
    }

    private void handleAction() {
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = etTaskName.getText().toString();
                String taskDesc = etTaskDesc.getText().toString();
                Task task = new Task();
                if(TextUtils.isEmpty(taskName)) {
                    showError(taskNameCanNotBeEmpty);
                    return;
                }
                task.setName(taskName);
                task.setDescription(taskDesc);
                taskViewModel.insert(task);
                Toasty.normal(getActivity(),taskHasBeenAdded, Toast.LENGTH_SHORT ).show();
                getActivity().finish();
            }
        });

    }

    private void showError(String taskNameCanNotBeEmpty) {
        Toasty.error(getActivity(), taskNameCanNotBeEmpty, Toast.LENGTH_SHORT).show();
    }

}
