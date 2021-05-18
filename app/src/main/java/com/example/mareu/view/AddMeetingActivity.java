package com.example.mareu.view;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mareu.MainApplication;
import com.example.mareu.R;
import com.example.mareu.ViewModelFactory;
import com.example.mareu.model.AddMeetingViewModel;
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
    TextInputLayout addLocationMeetingLyt;

    @BindView(R.id.add_meeting_membersLyt)
    TextInputLayout addMembersMeetingLyt;

    @BindView(R.id.add_meeting_subjectLyt)
    TextInputLayout addSubjectMeetingLyt;

    @BindView(R.id.add_meeting_timeLyt)
    TextInputLayout addTimeMeetingLyt;

    @BindView(R.id.add_meeting_timeEdit)
    TextInputEditText addTimeMeeting;

    @BindView(R.id.add_meeting_dateEdit)
    TextInputEditText addDateMeeting;

    @BindView(R.id.add_meeting_dateLyt)
    TextInputLayout addDateMeetingLyt;



    private int imageMeeting;
    final Calendar myCalendar = Calendar.getInstance();
    private TimePickerDialog mTimePickerDialog;
    private Application mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mApplication = MainApplication.getApplication();
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
        AddMeetingViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance())
                .get(AddMeetingViewModel.class);

        addSubjectMeetingLyt.getEditText().addTextChangedListener(new TextWatcher() {
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


        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> addDateMeeting
                .setText(viewModel.getDateMeetingFormat(dayOfMonth, month, year));

        addDateMeeting.setOnClickListener(v -> {
            int year = myCalendar.get(Calendar.YEAR);
            int month = myCalendar.get(Calendar.MONTH);
            int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddMeetingActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth, date, year, month, dayOfMonth);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        addTimeMeeting.setOnClickListener(v -> {
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minutes = myCalendar.get(Calendar.MINUTE);
            mTimePickerDialog = new TimePickerDialog(AddMeetingActivity.this,
                    (tp, sHour, sMinute) -> addTimeMeeting.setText(viewModel
                            .getTimeMeetingFormat(sHour,sMinute)), hour, minutes, true);
            mTimePickerDialog.show();
        });

        addMeeting.setOnClickListener(v -> {

            String timeMeeting = addTimeMeetingLyt.getEditText().getText().toString();
            String locationMeeting = addLocationMeetingLyt.getEditText().getText().toString();
            String membersMeeting = addMembersMeetingLyt.getEditText().getText().toString();
            String subjectMeeting = addSubjectMeetingLyt.getEditText().getText().toString();
            String dateMeeting = addDateMeetingLyt.getEditText().getText().toString();


            viewModel.addMeeting(subjectMeeting
                    , timeMeeting
                    , dateMeeting
                    , locationMeeting
                    , membersMeeting
                    , imageMeeting);


        });
        viewModel.getAddMeetingViewStateLiveData().observe(this, addMeetingViewState -> {
            addMembersMeetingLyt.setError(addMeetingViewState.getEmailError());
            addLocationMeetingLyt.setError(addMeetingViewState.getLocationError());
            addDateMeetingLyt.setError(addMeetingViewState.getDateError());
            addTimeMeetingLyt.setError(addMeetingViewState.getTimeError());
        });

        viewModel.onMeetingAdded().observe(this, this::addMeetingWithSuccess);

    }

    private void addMeetingWithSuccess(boolean isSuccess) {
        if (isSuccess) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(),mApplication.getString(R.string.error_add_meeting)
                    , Toast.LENGTH_LONG).show();
        }

    }

}

