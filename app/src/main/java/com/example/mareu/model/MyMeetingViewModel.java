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

    public MyMeetingViewModel(MyMeetingRepository myMeetingRepository,Application application) {
        mMyMeetingRepository = myMeetingRepository;
        mApplication = application;
        LiveData<List<MyMeeting>> meetingsLiveData = myMeetingRepository.getListMeetings();
        mListMeetingsMediatorLiveData.addSource(meetingsLiveData, new Observer<List<MyMeeting>>() {
            @Override
            public void onChanged(List<MyMeeting> myMeetingList) {
                combine(myMeetingList, searchQueryDateMutableLiveData.getValue());
            }
        });
        mListMeetingsMediatorLiveData.addSource(searchQueryDateMutableLiveData, new Observer<String>() {
            @Override
            public void onChanged(String searchQuery) {
                combine(meetingsLiveData.getValue(), searchQuery);
            }
        });


        mListMeetingsMediatorLiveData.addSource(searchQueryLocationMutableLiveData, new Observer<String>() {
            @Override
            public void onChanged(String searchQuery) {
                combine(meetingsLiveData.getValue(), searchQuery);
            }
        });


    }

    private void combine(@Nullable List<MyMeeting> meetings, @Nullable String searchQuery) {
        if (meetings == null) {
            return;
        }
        if (searchQuery == null) {
            mListMeetingsMediatorLiveData.setValue(meetings);
        } else {
            List<MyMeeting> results = new ArrayList<>();

            for (MyMeeting meeting : meetings) {
                if (meeting.getLocationMeeting().toLowerCase().contains(searchQuery)) {
                    results.add(meeting);
                } else if (meeting.getDateMeeting().toLowerCase().contains(searchQuery)){
                    results.add(meeting);

                }


            }
            mListMeetingsMediatorLiveData.setValue(results);

        }

    }


    public LiveData<List<MyMeeting>> getListMeetingsLiveData() {

        return mListMeetingsMediatorLiveData;
    }

    public String getMeetingFormat (MyMeeting meeting) {
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy/MM/dd");
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
