package com.example.mareu;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.matchers.MyViewAction;
import com.example.mareu.matchers.RecyclerViewItemCountAssertion;
import com.example.mareu.view.MyListMeetingsActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MyMeetingInstrumentedTest {

    @Rule
    public ActivityTestRule<MyListMeetingsActivity> mActivityTestRule = new ActivityTestRule<>(MyListMeetingsActivity.class);


    @Test
    public void checkIfClickAddButtonAddMeetingPagerIsDisplayed() {
        onView(withId(R.id.list_meeting_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.add_new_meeting)).perform(click());
        onView(withId(R.id.add_meeting_activity)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfAddMeetingIsWorking() {
        addMeeting();
    }

    @Test
    public void checkWhenEnterLocationToFilterListMeetingsIsFilteredByThisLocation() {
        addMeeting();
        onView(withId(R.id.filter))
                .perform(click());
        onView(withText("Filter by location"))
                .perform(click());
        onView(withId(R.id.filter_by_location_edit_text))
                .perform(click())
                .perform(typeText("Paris"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.list_meeting_textview_meeting_information))
                .check(matches(withText(containsString("Paris"))));
        onView(allOf(withId(R.id.recycler_view), isDisplayed()))
                .check(new RecyclerViewItemCountAssertion(1));

    }

    @Test
    public void checkWhenEnterDateToFilterListMeetingsIsFilteredByThisDate() {
        addMeeting();
        onView(withId(R.id.filter))
                .perform(click());
        onView(withText("Filter by date"))
                .perform(click());
        onView(withId(R.id.filter_by_date_edit_text))
                .perform(click())
                .perform(typeText("1/5/2021"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.list_meeting_textview_meeting_information))
                .check(matches(withText(containsString("1/5/2021"))));
        onView(allOf(withId(R.id.recycler_view), isDisplayed()))
                .check(new RecyclerViewItemCountAssertion(1));

    }

    @Test
    public void checkWhenEnterDateAndLocationToFilterListMeetingsIsFilteredByThisDateAndLocation() {
        addMeeting();
        onView(withId(R.id.filter))
                .perform(click());
        onView(withText("Filter by date"))
                .perform(click());
        onView(withId(R.id.filter_by_date_edit_text))
                .perform(click())
                .perform(typeText("30/5/2021"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.filter))
                .perform(click());
        onView(withText("Filter by location"))
                .perform(click());
        onView(withId(R.id.filter_by_location_edit_text))
                .perform(click())
                .perform(typeText("Paris"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.list_meeting_textview_meeting_information))
                .check(matches(withText(containsString("30/5/2021"))))
                .check(matches(withText(containsString("Paris"))));
        onView(allOf(withId(R.id.recycler_view), isDisplayed()))
                .check(new RecyclerViewItemCountAssertion(1));

    }

    @Test
    public void checkUpdateListMeetingsIsWorking(){
        addMeeting();
        onView(withId(R.id.filter))
                .perform(click());
        onView(withText("Filter by date"))
                .perform(click());
        onView(withId(R.id.filter_by_date_edit_text))
                .perform(click())
                .perform(typeText("30/5/2021"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.filter))
                .perform(click());
        onView(withText("All meetings"))
                .perform(click());
        onView(allOf(withId(R.id.recycler_view), isDisplayed()))
                .check(new RecyclerViewItemCountAssertion(3));


    }


    @Test
    public void checkRemoveMeetingIsWorking() {
        addMeeting();
        int ITEM_LIST_MEETING = 3;
        onView(allOf(ViewMatchers.withId(R.id.recycler_view), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, MyViewAction.deleteMeeting(R.id.list_meeting_imagebutton_delete_meeting)))
        .check(new RecyclerViewItemCountAssertion(ITEM_LIST_MEETING - 1));

    }

    private void addMeeting() {
        onView(withId(R.id.add_new_meeting)).perform(click());
        onView(withId(R.id.add_meeting_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.add_meeting_imageview_meeting)).perform(click()).perform();
        onView(withId(R.id.add_meeting_subEdit)).perform(click())
                .perform(typeText("Reunion A")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_meeting_dateLyt)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(setDate(2021, 5, 30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_meeting_timeLyt)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(15,30 ));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_meeting_locationEdit)).perform(click())
                .perform(typeText("Paris")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_meeting_membersEdit)).perform(click())
                .perform(typeText("tandjigora@hotmail.com"))
                .perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_meeting_btn_save))
                .perform(MyViewAction.handleConstraints(click(),isDisplayingAtLeast(65)));
        onView(withId(R.id.add_new_meeting)).perform(click());
        onView(withId(R.id.add_meeting_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.add_meeting_imageview_meeting)).perform(click()).perform();
        onView(withId(R.id.add_meeting_subEdit)).perform(click())
                .perform(typeText("Reunion B")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_meeting_dateLyt)).perform(click());
        onView(isAssignableFrom(DatePicker.class))
                .perform(setDate(2021, 5, 1));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_meeting_timeLyt)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(setTime(16,30 ));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_meeting_locationEdit)).perform(click())
                .perform(typeText("Lyon")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_meeting_membersEdit)).perform(click())
                .perform(typeText("tandjigora@hotmail.com"))
                .perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_meeting_btn_save))
                .perform(MyViewAction.handleConstraints(click(),isDisplayingAtLeast(65)));
        onView(withId(R.id.add_new_meeting)).perform(click());
        onView(withId(R.id.add_meeting_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.add_meeting_imageview_meeting)).perform(click()).perform();
        onView(withId(R.id.add_meeting_subEdit)).perform(click())
                .perform(typeText("Reunion C")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_meeting_dateLyt)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(setDate(2021, 5, 30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_meeting_timeLyt)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(setTime(15,30 ));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_meeting_locationEdit)).perform(click())
                .perform(typeText("Lille")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_meeting_membersEdit)).perform(click())
                .perform(typeText("tandjigora@hotmail.com")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_meeting_btn_save))
                .perform(MyViewAction.handleConstraints(click(),isDisplayingAtLeast(65)));


    }
}