package com.example.mareu.matchers;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MyViewAction {
    @NonNull
    public static ViewAction deleteMeeting(final int id) {
        return new ViewAction() {
            @Nullable
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @NonNull
            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, @NonNull View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    public static ViewAction handleConstraints(final ViewAction action, final Matcher<View> constraints)
    {
        return new ViewAction()
        {
            @Override
            public Matcher<View> getConstraints()
            {
                return constraints;
            }

            @Override
            public String getDescription()
            {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view)
            {
                action.perform(uiController, view);
            }
        };
    }


}
