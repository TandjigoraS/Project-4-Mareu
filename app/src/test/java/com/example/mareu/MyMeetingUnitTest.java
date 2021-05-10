package com.example.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

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

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class MyMeetingUnitTest {

    @Rule
    public final InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    MutableLiveData<List<MyMeeting>> listMeetingsMutableLiveData = new MutableLiveData<>();

    MyMeetingRepository myMeetingRepository = Mockito.mock(MyMeetingRepository.class);

    private MyMeetingViewModel myMeetingViewModel;



    @Before
    public void setUp() {


        Mockito.doReturn(listMeetingsMutableLiveData).when(myMeetingRepository).getListMeetings();

        myMeetingViewModel = new MyMeetingViewModel(myMeetingRepository, MainApplication.getApplication());

    }


    @Test
    public void nominal_case() throws InterruptedException {
        //Given

        listMeetingsMutableLiveData.setValue(generateMeetingsList());


        List<MyMeeting> result = UnitTestUtils.getOrAwaitValue(myMeetingViewModel.getListMeetingsLiveData());

        // Then
        assertEquals(generateMeetingsList(), result);

        Mockito.verify(myMeetingRepository, Mockito.times(1)).getListMeetings();


        Mockito.verifyNoMoreInteractions(myMeetingRepository);


    }

    @Test
    public void when_removeMeeting() {

        //Given
        MyMeeting meetingToDelete = new MyMeeting(
                "Reunion C"
                , "10H00"
                , "03/05/2021"
                , "Salle 223"
                , "tandjigora@gmail.com"
                , R.drawable.blue
        );


        // When
        myMeetingViewModel.removeMeeting(meetingToDelete);


        //Then

        Mockito.verify(myMeetingRepository, Mockito.times(1)).getListMeetings();

        Mockito.verify(myMeetingRepository, Mockito.times(1)).removeMeeting(meetingToDelete);

        Mockito.verifyNoMoreInteractions(myMeetingRepository);

    }

    @Test
    public void when_filterMyListMeetingsByLocationAndDate() throws InterruptedException {

        //Given
        listMeetingsMutableLiveData.setValue(generateMeetingsList());

        // When
        myMeetingViewModel.filterMeetingByLocation("223");
        myMeetingViewModel.filterMeetingByDate("01/05/2021");


        //Then
        List<MyMeeting> result = UnitTestUtils.getOrAwaitValue(myMeetingViewModel.getListMeetingsLiveData());

        assertEquals(generateMeetingsListFilteredByLocationAndDate(), result);

        Mockito.verify(myMeetingRepository, Mockito.times(1)).getListMeetings();

        Mockito.verifyNoMoreInteractions(myMeetingRepository);


    }


    private List<MyMeeting> generateMeetingsList() {
        List<MyMeeting> result = new ArrayList<>();

        result.add(
                new MyMeeting(
                        "Reunion A"
                        , "10H00"
                        , "03/05/2021"
                        , "Salle 221"
                        , "tandjigora@gmail.com"
                        , R.drawable.blue
                ));
        result.add(
                new MyMeeting(
                        "Reunion B"
                        , "11H00"
                        , "01/05/2021"
                        , "Salle 223"
                        , "tandjigora@gmail.com"
                        , R.drawable.grey
                ));


        return result;
    }


    private List<MyMeeting> generateMeetingsListFilteredByLocationAndDate() {
        List<MyMeeting> listMeetingsFilteredByLocation = new ArrayList<>();

        listMeetingsFilteredByLocation.add(
                new MyMeeting(
                        "Reunion B"
                        , "11H00"
                        , "01/05/2021"
                        , "Salle 223"
                        , "tandjigora@gmail.com"
                        , R.drawable.grey
                ));
        return listMeetingsFilteredByLocation;
    }


}