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
import static org.junit.Assert.assertNull;


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
    public void when_myListMeetingsHaveMeeting() throws InterruptedException {
        //Given

        listMeetingsMutableLiveData.setValue(generateMeetingsList());


        List<MyMeeting> result = UnitTestUtils.getOrAwaitValue(myMeetingViewModel.getListMeetingsLiveData());

        // Then
        assertEquals(generateMeetingsList(), result);

        Mockito.verify(myMeetingRepository, Mockito.times(1)).getListMeetings();


        Mockito.verifyNoMoreInteractions(myMeetingRepository);


    }
    @Test
    public void when_myListMeetingsDoesNotHaveMeeting() throws InterruptedException {
        //Given

        listMeetingsMutableLiveData.setValue(null);


        List<MyMeeting> result = UnitTestUtils.getOrAwaitValue(myMeetingViewModel.getListMeetingsLiveData());

        // Then
        assertNull(result);

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
                , "Paris"
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
    public void when_filterMyListMeetingsWithLocationAndDate() throws InterruptedException {

        //Given
        listMeetingsMutableLiveData.setValue(generateMeetingsList());

        // When
        myMeetingViewModel.filterMeetingByDate("01/05/2021");
        myMeetingViewModel.filterMeetingByLocation("Paris");



        //Then
        List<MyMeeting> result = UnitTestUtils.getOrAwaitValue(myMeetingViewModel.getListMeetingsLiveData());

        assertEquals(generateMeetingsListFilteredLocationAndDate(), result);

        Mockito.verify(myMeetingRepository, Mockito.times(1)).getListMeetings();

        Mockito.verifyNoMoreInteractions(myMeetingRepository);


    }
    @Test
    public void when_filterMyListMeetingsWithLocation() throws InterruptedException {

        //Given
        listMeetingsMutableLiveData.setValue(generateMeetingsList());

        // When
        myMeetingViewModel.filterMeetingByLocation("Paris");



        //Then
        List<MyMeeting> result = UnitTestUtils.getOrAwaitValue(myMeetingViewModel.getListMeetingsLiveData());

        assertEquals(generateMeetingsListFilteredLocation(), result);

        Mockito.verify(myMeetingRepository, Mockito.times(1)).getListMeetings();

        Mockito.verifyNoMoreInteractions(myMeetingRepository);


    }

    @Test
    public void when_filterMyListMeetingsWithDate() throws InterruptedException {

        //Given
        listMeetingsMutableLiveData.setValue(generateMeetingsList());

        // When
        myMeetingViewModel.filterMeetingByDate("03/05/2021");


        //Then
        List<MyMeeting> result = UnitTestUtils.getOrAwaitValue(myMeetingViewModel.getListMeetingsLiveData());

        assertEquals(generateMeetingsListFilteredDate(), result);

        Mockito.verify(myMeetingRepository, Mockito.times(1)).getListMeetings();

        Mockito.verifyNoMoreInteractions(myMeetingRepository);


    }

    @Test
    public void when_filterMyListMeetingsWithDateEmptyAndLocationEmpty() throws InterruptedException {

        //Given
        listMeetingsMutableLiveData.setValue(generateMeetingsList());

        // When
        myMeetingViewModel.filterMeetingByDate("");
        myMeetingViewModel.filterMeetingByLocation("");


        //Then
        List<MyMeeting> result = UnitTestUtils.getOrAwaitValue(myMeetingViewModel.getListMeetingsLiveData());

        assertEquals(generateMeetingsList(), result);

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
                        , "Nantes"
                        , "tandjigora@gmail.com"
                        , R.drawable.blue
                ));
        result.add(
                new MyMeeting(
                        "Reunion B"
                        , "11H00"
                        , "01/05/2021"
                        , "Paris"
                        , "tandjigora@gmail.com"
                        , R.drawable.grey
                ));
        result.add(
                new MyMeeting(
                        "Reunion C"
                        , "11H00"
                        , "01/05/2021"
                        , "Lille"
                        , "tandjigora@gmail.com"
                        , R.drawable.grey
                ));


        return result;
    }


    private List<MyMeeting> generateMeetingsListFilteredLocation() {
        List<MyMeeting> listMeetingsFilteredByLocation = new ArrayList<>();

        listMeetingsFilteredByLocation.add(

                new MyMeeting(
                        "Reunion B"
                        , "11H00"
                        , "01/05/2021"
                        , "Paris"
                        , "tandjigora@gmail.com"
                        , R.drawable.grey
                ));
        return listMeetingsFilteredByLocation;
    }

    private List<MyMeeting> generateMeetingsListFilteredLocationAndDate() {
        List<MyMeeting> listMeetingsFilteredLocationAndDate = new ArrayList<>();

        listMeetingsFilteredLocationAndDate.add(
                new MyMeeting(
                        "Reunion B"
                        , "11H00"
                        , "01/05/2021"
                        , "Paris"
                        , "tandjigora@gmail.com"
                        , R.drawable.grey
                ));
        return listMeetingsFilteredLocationAndDate;
    }

    private List<MyMeeting> generateMeetingsListFilteredDate() {
        List<MyMeeting> listMeetingsFilteredDate = new ArrayList<>();

        listMeetingsFilteredDate.add(
                new MyMeeting(
                        "Reunion A"
                        , "10H00"
                        , "03/05/2021"
                        , "Nantes"
                        , "tandjigora@gmail.com"
                        , R.drawable.blue
                ));
        return listMeetingsFilteredDate;
    }


}