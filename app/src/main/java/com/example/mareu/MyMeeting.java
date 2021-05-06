package com.example.mareu;


import android.graphics.drawable.Drawable;

import java.util.Objects;
import java.util.Random;

public class MyMeeting {


    public String mTimeMeeting;
    public String mLocationMeeting;
    public String mMembersMeeting;
    public String mSubjectMeeting;
    public String mDateMeeting;
    public int mImagesMeeting;




    public MyMeeting(String subjectMeeting, String timeMeeting,String dateMeeting, String locationMeeting, String membersOfMeeting, int imagesMeeting ) {

        this.mSubjectMeeting = subjectMeeting;
        this.mTimeMeeting = timeMeeting;
        this.mLocationMeeting = locationMeeting;
        this.mMembersMeeting = membersOfMeeting;
        this.mImagesMeeting = imagesMeeting;
        this.mDateMeeting = dateMeeting;


    }



    public String getSubjectMeeting() {
        return mSubjectMeeting;
    }

    public void setSubjectMeeting(String subjectMeeting) {
        mSubjectMeeting = subjectMeeting;
    }

    public String getTimeMeeting() {
        return mTimeMeeting;
    }

    public void setTimeMeeting(String timeMeeting) {
        mTimeMeeting = timeMeeting;
    }

    public String getLocationMeeting() {
        return mLocationMeeting;
    }

    public void setLocationMeeting(String locationMeeting) {
        mLocationMeeting = locationMeeting;
    }

    public String getMembersMeeting() {
        return mMembersMeeting;
    }

    public void setMembersMeeting(String membersMeeting) {
        mMembersMeeting = membersMeeting;
    }

    public String getDateMeeting() {
        return mDateMeeting;
    }

    public void setDateMeeting(String dateMeeting) {
        mDateMeeting = dateMeeting;
    }

    public int getImagesMeeting() {
        return mImagesMeeting;
    }

    public void setImagesMeeting(int imagesMeeting) {
        mImagesMeeting = imagesMeeting;
    }


    @Override
    public String toString() {
        return "MyMeeting{" +
                "mTimeMeeting='" + mTimeMeeting + '\'' +
                ", mLocationMeeting='" + mLocationMeeting + '\'' +
                ", mMembersMeeting='" + mMembersMeeting + '\'' +
                ", mSubjectMeeting='" + mSubjectMeeting + '\'' +
                ", mDateMeeting='" + mDateMeeting + '\'' +
                ", mImagesMeeting=" + mImagesMeeting +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyMeeting myMeeting = (MyMeeting) o;
        return mImagesMeeting == myMeeting.mImagesMeeting &&
                Objects.equals(mTimeMeeting, myMeeting.mTimeMeeting) &&
                Objects.equals(mLocationMeeting, myMeeting.mLocationMeeting) &&
                Objects.equals(mMembersMeeting, myMeeting.mMembersMeeting) &&
                Objects.equals(mSubjectMeeting, myMeeting.mSubjectMeeting) &&
                Objects.equals(mDateMeeting, myMeeting.mDateMeeting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mTimeMeeting, mLocationMeeting, mMembersMeeting, mSubjectMeeting, mDateMeeting, mImagesMeeting);
    }

}
