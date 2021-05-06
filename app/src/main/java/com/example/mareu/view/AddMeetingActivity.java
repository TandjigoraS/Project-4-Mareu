package com.example.mareu.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mareu.R;
import com.example.mareu.ViewModelFactory;
import com.example.mareu.model.AddMeetingViewModel;
import com.example.mareu.model.AddMeetingViewState;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddMeetingActivity extends AppCompatActivity {


    //UI Components

    @BindView(R.id.add_meeting_btn_save)
    MaterialButton addMeeting;

    @BindView(R.id.add_meeting_imageview_meeting)
    ImageView addImageMeeting;

    @BindView(R.id.add_meeting_imageview_meeting2)
    ImageView addImageMeeting2;

    @BindView(R.id.add_meeting_imageview_meeting3)
    ImageView addImageMeeting3;

    @BindView(R.id.add_meeting_locationLyt)
    TextInputLayout addLocationMeeting;

    @BindView(R.id.add_meeting_membersLyt)
    TextInputLayout addMembersMeeting;

    @BindView(R.id.add_meeting_subjectLyt)
    TextInputLayout addSubjectMeeting;

    @BindView(R.id.add_meeting_timeLyt)
    TextInputLayout addTimeMeeting;

    @BindView(R.id.add_meeting_dateEdit)
    TextInputEditText addDateMeeting;

    @BindView(R.id.add_meeting_date)
    TextInputLayout addDateMeetingLyt;

    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView mAutoCompleteTextView;


    private int imageMeeting;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.time_meeting));
        myAdapter.setDropDownViewResource(R.layout.dropdown_menu);
        mAutoCompleteTextView.setAdapter(myAdapter);
        Glide.with(this)
                .load(R.drawable.blue)
                .apply(RequestOptions.circleCropTransform())
                .into(addImageMeeting);
        Glide.with(this)
                .load(R.drawable.grey)
                .apply(RequestOptions.circleCropTransform())
                .into(addImageMeeting2);
        Glide.with(this)
                .load(R.drawable.purple)
                .apply(RequestOptions.circleCropTransform())
                .into(addImageMeeting3);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoomout);
        addImageMeeting.setOnClickListener(v -> {
            imageMeeting = R.drawable.blue;
            addImageMeeting.clearAnimation();
            addImageMeeting2.startAnimation(animation);
            addImageMeeting3.startAnimation(animation);
        });
        addImageMeeting2.setOnClickListener(v -> {
            imageMeeting = R.drawable.grey;
            addImageMeeting2.clearAnimation();
            addImageMeeting.startAnimation(animation);
            addImageMeeting3.startAnimation(animation);
        });
        addImageMeeting3.setOnClickListener(v -> {
            imageMeeting = R.drawable.purple;
            addImageMeeting3.clearAnimation();
            addImageMeeting.startAnimation(animation);
            addImageMeeting2.startAnimation(animation);
        });
        AddMeetingViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);

        addSubjectMeeting.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addMeeting.setEnabled(s.length() > 0);
            }
        });


        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            addDateMeeting.setText(viewModel.getDateMeetingFormat(dayOfMonth, month, year));
        };

        addDateMeeting.setOnClickListener(v -> {
            int year = myCalendar.get(Calendar.YEAR);
            int month = myCalendar.get(Calendar.MONTH);
            int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddMeetingActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth, date, year, month, dayOfMonth);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });


        addMeeting.setOnClickListener(v -> {

            String timeMeeting = addTimeMeeting.getEditText().getText().toString();
            String locationMeeting = addLocationMeeting.getEditText().getText().toString();
            String membersMeeting = addMembersMeeting.getEditText().getText().toString();
            String subjectMeeting = addSubjectMeeting.getEditText().getText().toString();
            String dateMeeting = addDateMeetingLyt.getEditText().getText().toString();
            if (imageMeeting == 0) {
                imageMeeting = R.drawable.yellow;
            }

            viewModel.addMeeting(subjectMeeting, timeMeeting, dateMeeting, locationMeeting, membersMeeting, imageMeeting);


        });
        viewModel.getAddMeetingViewStateLiveData().observe(this, addMeetingViewState -> {
            if (!addMeetingViewState.isEmailValid()) {
                addMembersMeeting.setError("Address email is wrong or empty");
            } else {
                addMembersMeeting.setError(null);
            }
            if (!addMeetingViewState.isLocationValid()) {
                addLocationMeeting.setError("The location of the meeting is empty");
            } else {
                addLocationMeeting.setError(null);
            }
            if (!addMeetingViewState.isDateNotEmpty()) {
                addDateMeetingLyt.setError("The date wasn't selected");

            } else {
                addDateMeetingLyt.setError(null);
            }

        });

        viewModel.onMeetingAdded().observe(this, isSuccess -> addMeetingWithSuccess(isSuccess));

    }

    private void addMeetingWithSuccess(boolean isSuccess) {
        if (isSuccess) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Error : Meeting doesn't add in the list", Toast.LENGTH_LONG).show();
        }

    }

}

