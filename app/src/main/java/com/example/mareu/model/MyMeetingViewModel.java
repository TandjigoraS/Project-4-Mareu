package com.example.mareu.model;


import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mareu.MyMeeting;
import com.example.mareu.R;
import com.example.mareu.repository.MyMeetingRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class MyMeetingViewModel extends ViewModel {

    private final MyMeetingRepository mMyMeetingRepository;
    private final MediatorLiveData<List<MyMeeting>> mListMeetingsMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<String> searchQueryDateMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> searchQueryLocationMutableLiveData = new MutableLiveData<>();
    private final Application mApplication;

    public MyMeetingViewModel(MyMeetingRepository myMeetingRepository, Application application) {
        mMyMeetingRepository = myMeetingRepository;
        mApplication = application;
        LiveData<List<MyMeeting>> meetingsLiveData = myMeetingRepository.getListMeetings();
        mListMeetingsMediatorLiveData.addSource(meetingsLiveData, myMeetingList -> combine(
                myMeetingList,
                searchQueryDateMutableLiveData.getValue(),
                searchQueryLocationMutableLiveData.getValue())
        );
        mListMeetingsMediatorLiveData.addSource(searchQueryDateMutableLiveData, dateSearchQuery -> combine(
                meetingsLiveData.getValue(),
                dateSearchQuery,
                searchQueryLocationMutableLiveData.getValue()

                )
        );


        mListMeetingsMediatorLiveData.addSource(searchQueryLocationMutableLiveData, locationSearchQuery -> combine(
                meetingsLiveData.getValue(),
                searchQueryDateMutableLiveData.getValue(),
                locationSearchQuery

                )
        );


    }

    private void combine(@Nullable List<MyMeeting> meetings, @Nullable String dateSearchQuery, @Nullable String locationSearchQuery) {
        if (meetings == null) {
            return;
        }
        if(dateSearchQuery == null && locationSearchQuery == null ){
            mListMeetingsMediatorLiveData.setValue(meetings);
        } else {
            List<MyMeeting> results = new ArrayList<>();

            for (MyMeeting meeting : meetings) {

                boolean hasDateQuery = dateSearchQuery != null && !dateSearchQuery.trim().isEmpty();
                boolean hasLocationQuery = locationSearchQuery != null && !locationSearchQuery.trim().isEmpty();
                boolean isDateOk = meeting.getDateMeeting().toLowerCase().contains(dateSearchQuery);
                boolean isLocationOk = meeting.getLocationMeeting().toLowerCase().contains(locationSearchQuery);

                if ((isLocationOk || hasLocationQuery) && (isDateOk || hasDateQuery)) {
                    results.add(meeting);

                }
            }
            mListMeetingsMediatorLiveData.setValue(results);

        }
    }


    public LiveData<List<MyMeeting>> getListMeetingsLiveData() {

        return mListMeetingsMediatorLiveData;
    }

    public String getMeetingFormat(MyMeeting meeting) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return mApplication.getString(R.string.meeting_format,
                meeting.getSubjectMeeting(),
                meeting.getTimeMeeting(),
                String.format(meeting.getDateMeeting(), dateTimeFormatter),
                meeting.getLocationMeeting());
    }


    public void removeMeeting(MyMeeting meeting) {
        mMyMeetingRepository.removeMeeting(meeting);
    }

    public void filterMeetingByDate(String newText) {
        searchQueryDateMutableLiveData.setValue(newText.toLowerCase());
    }

    public void filterMeetingByLocation(String newText) {
        searchQueryLocationMutableLiveData.setValue(newText.toLowerCase());
    }


}
