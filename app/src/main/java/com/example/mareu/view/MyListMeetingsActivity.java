package com.example.mareu.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.MyListMeetingsRecyclerViewAdapter;
import com.example.mareu.R;
import com.example.mareu.ViewModelFactory;
import com.example.mareu.model.MyMeetingViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyListMeetingsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    // UI components

    @BindView(R.id.material_toolbar)
    MaterialToolbar mMaterialToolbar;

    @BindView(R.id.add_new_meeting)
    FloatingActionButton mFab;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;



    private MyMeetingViewModel myMeetingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meetings);
        ButterKnife.bind(this);
        setSupportActionBar(mMaterialToolbar);
        myMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MyMeetingViewModel.class);
        final MyListMeetingsRecyclerViewAdapter adapter = new MyListMeetingsRecyclerViewAdapter(myMeetingViewModel);
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
        MenuItem searchItem = menu.findItem(R.id.searchView);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return true;
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        myMeetingViewModel.filterMeetingByDate(newText.toLowerCase());
        myMeetingViewModel.filterMeetingByLocation(newText.toLowerCase());


        return true;
    }
}