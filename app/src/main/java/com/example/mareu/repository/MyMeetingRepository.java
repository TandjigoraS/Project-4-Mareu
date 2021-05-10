package com.example.mareu.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.mareu.MyMeeting;

import java.util.ArrayList;
import java.util.List;

public class MyMeetingRepository {

    private final MediatorLiveData<List<MyMeeting>> mMyListMeetings = new MediatorLiveData<>();
    private final List<MyMeeting> mMeeting = new ArrayList<>();


    public LiveData<List<MyMeeting>> getListMeetings() {
        return mMyListMeetings;
    }

    public void addMeeting(MyMeeting meeting) {
        mMeeting.add(meeting);
        mMyListMeetings.setValue(mMeeting);
    }

    public void removeMeeting(MyMeeting meeting) {
        mMeeting.remove(meeting);
        mMyListMeetings.setValue(mMeeting);
    }

}



