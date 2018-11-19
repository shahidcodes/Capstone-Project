package ml.shahidkamal.flatmatestaskreminder.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import ml.shahidkamal.flatmatestaskreminder.utils.Analytics;
import ml.shahidkamal.flatmatestaskreminder.utils.Constants;
import ml.shahidkamal.flatmatestaskreminder.R;
import ml.shahidkamal.flatmatestaskreminder.model.Task;
import ml.shahidkamal.flatmatestaskreminder.room.TaskViewModel;
import ml.shahidkamal.flatmatestaskreminder.scheduler.TaskScheduler;

public class FragmentAddTask extends Fragment {
    private static final String TAG = "FragmentAddTask";

    @BindView(R.id.etTaskName)
    EditText etTaskName;
    @BindView(R.id.etTaskDesc)
    EditText etTaskDesc;
    @BindView(R.id.btnAddTask)
    Button btnAddTask;
    @BindView(R.id.spinnerOpts)
    Spinner spinnerOpts;
    @BindView(R.id.cbRecurring)
    CheckBox cbRecurring;
    @BindArray(R.array.days_array)
    String[] daysArray;
    @BindView(R.id.btnDelTask)
    Button btnDelTask;
    private TaskViewModel taskViewModel;
    @BindString(R.string.task_name_required)
    String taskNameCanNotBeEmpty;
    @BindString(R.string.task_added)
    String taskHasBeenAdded;
    @BindString(R.string.task_deleted) String taskDeleted;
    @BindString(R.string.task_updated) String taskUpdated;
    @BindString(R.string.btn_label_update_task) String btnLabelUpdateTask;
    String selectOption;
    Task intentTask;

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
        View view = inflater.inflate(R.layout.fragment_fragment_add_task, container, false);
        ButterKnife.bind(this, view);
        handleAction();
        Intent intent = getActivity().getIntent();
        if(intent != null){
            intentTask = (Task) intent.getSerializableExtra(Constants.INTENT_KEY_JOB_OBJECT);
            if( intentTask!= null) {
                populateEditData(intentTask);
            }
        }
        return view;
    }

    private void populateEditData(Task task) {
        Log.d(TAG, "populateEditData" + task.getTaskId());
        cbRecurring.setChecked(task.isRecurring());
        etTaskName.setText(task.getName());
        etTaskDesc.setText(task.getDescription());
        spinnerOpts.setSelection(Arrays.asList(daysArray).indexOf(task.getRecurringDay()));
        btnDelTask.setVisibility(View.VISIBLE);
        btnAddTask.setText(btnLabelUpdateTask);
    }

    private void handleAction() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOpts.setAdapter(adapter);
        spinnerOpts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectOption = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectOption = daysArray[0];
            }
        });

        btnDelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCurrentTask();
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = etTaskName.getText().toString();
                String taskDesc = etTaskDesc.getText().toString();
                Task task;
                if(intentTask != null) {
                    task = intentTask;
                }else{
                    task = new Task();
                }
                if (TextUtils.isEmpty(taskName)) {
                    showError(taskNameCanNotBeEmpty);
                    return;
                }
                task.setName(taskName);
                task.setDescription(taskDesc);
                task.setRecurringDay(selectOption);
                task.setRecurring(cbRecurring.isChecked());
                TaskScheduler utils = new TaskScheduler(getContext());
                if(intentTask != null){
                    // update
                    taskViewModel.update(task);
                    utils.cancelSchedule(task);
                }else {
                    taskViewModel.insert(task);
                }
                utils.scheduleTask(task);
                Toasty.normal(getActivity(), taskHasBeenAdded, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

    }

    private void deleteCurrentTask() {
        if(intentTask!=null){
            taskViewModel.delete(intentTask);
            Toasty.normal(getActivity(), taskDeleted, Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }else {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "DELETE TASK ITEM NULL INTENT");
            Analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }
    }

    private void showError(String taskNameCanNotBeEmpty) {
        Toasty.error(getActivity(), taskNameCanNotBeEmpty, Toast.LENGTH_SHORT).show();
    }

}
