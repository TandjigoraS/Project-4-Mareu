package com.example.mareu;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.model.AddMeetingViewModel;
import com.example.mareu.model.MyMeetingViewModel;
import com.example.mareu.repository.MyMeetingRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sFactory;
    private final MyMeetingRepository mMyMeetingRepository;


    public ViewModelFactory(@NonNull MyMeetingRepository myMeetingRepository) {
        this.mMyMeetingRepository = myMeetingRepository;


    }

    public static ViewModelFactory getInstance() {
        if (sFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (sFactory == null) {
                    sFactory = new ViewModelFactory(
                            new MyMeetingRepository()
                    );
                }
            }
        }

        return sFactory;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MyMeetingViewModel.class)) {
            return (T) new MyMeetingViewModel(
                    mMyMeetingRepository,MainApplication.getApplication()
            );
        } else if (modelClass.isAssignableFrom(AddMeetingViewModel.class)) {
            return (T) new AddMeetingViewModel(
                    mMyMeetingRepository,MainApplication.getApplication()
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
