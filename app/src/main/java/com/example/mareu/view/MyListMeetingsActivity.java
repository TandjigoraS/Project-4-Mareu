package com.example.mareu.view;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.MainApplication;
import com.example.mareu.MyListMeetingsRecyclerViewAdapter;
import com.example.mareu.R;
import com.example.mareu.ViewModelFactory;
import com.example.mareu.model.MyMeetingViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyListMeetingsActivity extends AppCompatActivity {
    // UI components

    @BindView(R.id.material_toolbar)
    MaterialToolbar mMaterialToolbar;

    @BindView(R.id.add_new_meeting)
    FloatingActionButton mFab;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private MyMeetingViewModel myMeetingViewModel;
    private MyListMeetingsRecyclerViewAdapter adapter;
    private Application mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meetings);
        ButterKnife.bind(this);
        setSupportActionBar(mMaterialToolbar);
        mApplication = MainApplication.getApplication();
        myMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MyMeetingViewModel.class);
        adapter = new MyListMeetingsRecyclerViewAdapter(myMeetingViewModel);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myMeetingViewModel.getListMeetingsLiveData().observe(this, myMeetings -> adapter.setData(myMeetings));

        mFab.setOnClickListener(view -> {
            Intent intent = new Intent(MyListMeetingsActivity.this, AddMeetingActivity.class);
            startActivity(intent);
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AlertDialog.Builder builder;
        AlertDialog dialog;
        switch (id) {
            case R.id.filter_by_date:
                builder = new AlertDialog.Builder(this);
                builder.setTitle(mApplication.getString(R.string.date_title_alert_dialog));
                final View filterByDate = getLayoutInflater().inflate(R.layout.filter_by_date, null);
                builder.setView(filterByDate);
                builder.setPositiveButton(mApplication.getString(R.string.positive_button_alert_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText filter = filterByDate.findViewById(R.id.filter_by_date_edit_text);
                        myMeetingViewModel.filterMeetingByDate(filter.getText().toString().trim());
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.filter_by_location:
                builder = new AlertDialog.Builder(this);
                builder.setTitle(mApplication.getString(R.string.location_title_alert_dialog));
                final View filterByLocation = getLayoutInflater().inflate(R.layout.filter_by_location, null);
                builder.setView(filterByLocation);
                builder.setPositiveButton(mApplication.getString(R.string.positive_button_alert_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText filter = filterByLocation.findViewById(R.id.filter_by_location_edit_text);
                        myMeetingViewModel.filterMeetingByLocation(filter.getText().toString().trim());

                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.all_meetings:
                myMeetingViewModel.filterMeetingByDate(mApplication.getString(R.string.empty));
                myMeetingViewModel.filterMeetingByLocation(mApplication.getString(R.string.empty));
                break;
        }
        return true;
    }

}