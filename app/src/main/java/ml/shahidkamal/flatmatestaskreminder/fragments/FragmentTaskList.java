package ml.shahidkamal.flatmatestaskreminder.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ml.shahidkamal.flatmatestaskreminder.AddTaskActivity;
import ml.shahidkamal.flatmatestaskreminder.R;
import ml.shahidkamal.flatmatestaskreminder.adapter.AdapterTaskList;
import ml.shahidkamal.flatmatestaskreminder.model.Task;
import ml.shahidkamal.flatmatestaskreminder.room.TaskViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTaskList extends Fragment {
    private static final String TAG = "FragmentTaskList";
    @BindView(R.id.fabAddTask)
    FloatingActionButton fabAddTask;
    @BindView(R.id.rv_task_list)
    RecyclerView rvTaskList;
    TaskViewModel taskViewModel;

    public FragmentTaskList() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_task_list, container, false);
        ButterKnife.bind(this, view);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        final AdapterTaskList adapterTaskList = new AdapterTaskList(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTaskList.setLayoutManager(layoutManager);
        rvTaskList.setAdapter(adapterTaskList);
        rvTaskList.addItemDecoration(new DividerItemDecoration(rvTaskList.getContext(), layoutManager.getOrientation()));
        taskViewModel.getAllTasks().observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                Log.d(TAG, "onChanged: " + tasks.size());
                adapterTaskList.setTasks(tasks);
            }
        });
    }

}
