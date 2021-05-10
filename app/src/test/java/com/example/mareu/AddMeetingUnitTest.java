package com.example.mareu;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.model.AddMeetingViewModel;
import com.example.mareu.model.MyMeetingViewModel;
import com.example.mareu.repository.MyMeetingRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class AddMeetingUnitTest {

    @Rule
    public final InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    MyMeetingRepository myMeetingRepository = Mockito.spy(MyMeetingRepository.class);

    private AddMeetingViewModel mAddMeetingViewModel;

    @Before
    public void setUp() {

        mAddMeetingViewModel = new AddMeetingViewModel(myMeetingRepository, MainApplication.getApplication());

    }

    @Test
    public void when_addMeeting() throws InterruptedException {
        //Given
        MyMeeting myMeeting = new MyMeeting("Reunion A"
                , "10H00"
                , "03/05/2021"
                , "Salle 221"
                , "tandjigora@gmail.com"
                , R.drawable.blue);


         //When
        mAddMeetingViewModel.addMeeting("Reunion A"
                , "10H00"
                , "03/05/2021"
                , "Salle 221"
                , "tandjigora@gmail.com"
                , R.drawable.blue
        );


        // Then
        Mockito.verify(myMeetingRepository, Mockito.times(1)).addMeeting(myMeeting);

        Mockito.verifyNoMoreInteractions(myMeetingRepository);

    }

}
