package com.example.mareu.model;

public class AddMeetingViewState {

    private final boolean isEmailValid;
    private final boolean isLocationValid;
    private final boolean isDateSelected;
    private final boolean isTimeSelected;




    public AddMeetingViewState(boolean isEmailValid, boolean isLocationValid, boolean isDateSelected, boolean isTimeSelected) {
        this.isEmailValid = isEmailValid;
        this.isLocationValid = isLocationValid;
        this.isDateSelected = isDateSelected;
        this.isTimeSelected = isTimeSelected;
    }

    public boolean isEmailValid() {
        return isEmailValid;
    }

    public boolean isLocationValid() {
        return isLocationValid;
    }

    public boolean isDateSelected() { return isDateSelected; }

    public boolean isTimeSelected() { return isTimeSelected; }
}
