package com.example.mareu;


import java.util.Objects;

public class MyMeeting {


    public final String mTimeMeeting;
    public final String mLocationMeeting;
    public final String mMembersMeeting;
    public final String mSubjectMeeting;
    public final String mDateMeeting;
    public final int mImagesMeeting;




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

    public String getTimeMeeting() {
        return mTimeMeeting;
    }

    public String getLocationMeeting() {
        return mLocationMeeting;
    }

    public String getMembersMeeting() {
        return mMembersMeeting;
    }

    public String getDateMeeting() {
        return mDateMeeting;
    }

    public int getImagesMeeting() {
        return mImagesMeeting;
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
