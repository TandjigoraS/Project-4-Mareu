package com.example.mareu.model;

public class AddMeetingViewState {

    private final String mEmailError;
    private final String mLocationError;
    private final String mDateError;
    private final String mTimeError;




    public AddMeetingViewState(String emailError, String locationError, String dateError, String timeError) {
        mEmailError = emailError;
        mLocationError = locationError;
        mDateError = dateError;
        mTimeError = timeError;

    }

    public String getEmailError() {
        return mEmailError;
    }

    public String getLocationError() {
        return mLocationError;
    }

    public String getDateError() {
        return mDateError;
    }

    public String getTimeError() {
        return mTimeError;
    }

}
