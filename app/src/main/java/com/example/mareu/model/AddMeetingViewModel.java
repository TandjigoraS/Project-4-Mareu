package com.example.mareu.model;

import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.MyMeeting;
import com.example.mareu.R;
import com.example.mareu.repository.MyMeetingRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMeetingViewModel extends ViewModel {
    private static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private final MyMeetingRepository mMyMeetingRepository;
    private final Application mApplication;
    private final MutableLiveData<AddMeetingViewState> mAddMeetingViewStateMutableLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<Boolean> meetingAdded = new SingleLiveEvent<>();


    public AddMeetingViewModel(MyMeetingRepository myMeetingRepository, Application application) {
        mMyMeetingRepository = myMeetingRepository;
        mApplication = application;

    }


    public SingleLiveEvent<Boolean> onMeetingAdded() {
        return meetingAdded;
    }

    public void addMeeting(String subjectMeeting, String timeMeeting, String dateMeeting, String locationMeeting, String membersMeeting, int imageMeeting) {
        String[] membersAddressEmail = membersMeeting.split(mApplication.getString(R.string.comma));
        String locationError;
        String timeError;
        String dateError;
        String addressError;

        if (TextUtils.isEmpty(timeMeeting)) {
            timeError = mApplication.getString(R.string.time_error);;
        } else {
            timeError = null;
        }
        if (TextUtils.isEmpty(locationMeeting)) {
            locationError = mApplication.getString(R.string.location_error);;
        } else {
            locationError = null;
        }
        if (TextUtils.isEmpty(dateMeeting)) {
            dateError = mApplication.getString(R.string.date_error);;
        } else {
            dateError = null;
        }

        boolean addressIsRight = true;
        for (String address : membersAddressEmail) {
            String trimAddress = address.trim();
            Matcher matcher = EMAIL_ADDRESS.matcher(trimAddress);
            if ((!matcher.matches())) {
                addressIsRight = false;
            }
        }
        if (!addressIsRight) {
            addressError = mApplication.getString(R.string.address_error);
        } else {
            addressError = null;
        }

        if(imageMeeting == 0){
            imageMeeting = R.drawable.yellow;
        }

        if (locationError == null && addressError == null && dateError == null && timeError == null) {

            mMyMeetingRepository.addMeeting(new MyMeeting(subjectMeeting, timeMeeting, dateMeeting, locationMeeting, membersMeeting, imageMeeting));
            onMeetingAdded().setValue(true);
        } else {
            mAddMeetingViewStateMutableLiveData.setValue(new AddMeetingViewState(addressError, locationError, dateError, timeError));
        }

    }

    public LiveData<AddMeetingViewState> getAddMeetingViewStateLiveData() {
        return mAddMeetingViewStateMutableLiveData;
    }

    public String getDateMeetingFormat(int dayOfMonth, int month, int year) {
        month = month + 1;
        return mApplication.getString(R.string.date_meeting_format, dayOfMonth, month, year);
    }

    public String getTimeMeetingFormat(int hour, int minute) {
        return mApplication.getString(R.string.time_meeting_format, hour, minute);
    }

}



