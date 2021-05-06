package com.example.mareu.model;

public class AddMeetingViewState {

    private final boolean isEmailValid;
    private final boolean isLocationValid;
    private final boolean isDateNotEmpty;




    public AddMeetingViewState(boolean isEmailValid, boolean isLocationValid, boolean isDateNotEmpty) {
        this.isEmailValid = isEmailValid;
        this.isLocationValid = isLocationValid;
        this.isDateNotEmpty = isDateNotEmpty;
    }

    public boolean isEmailValid() {
        return isEmailValid;
    }

    public boolean isLocationValid() {
        return isLocationValid;
    }

    public boolean isDateNotEmpty() { return isDateNotEmpty; }
}
