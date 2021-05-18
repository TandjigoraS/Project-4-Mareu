package com.example.mareu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mareu.model.MyMeetingViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyListMeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MyListMeetingsRecyclerViewAdapter.ViewHolder> {

    private final List<MyMeeting> mMyMeetings = new ArrayList<>();
    private final MyMeetingViewModel mMyMeetingViewModel;


    public MyListMeetingsRecyclerViewAdapter(MyMeetingViewModel myMeetingViewModel) {
        mMyMeetingViewModel = myMeetingViewModel;

    }


    public void setData(List<MyMeeting> myMeetings) {
        mMyMeetings.clear();
        mMyMeetings.addAll(myMeetings);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyListMeetingsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_meeting, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyListMeetingsRecyclerViewAdapter.ViewHolder holder, int position) {
        MyMeeting myMeeting = mMyMeetings.get(position);
        holder.mInformationMeeting.setText(mMyMeetingViewModel.getMeetingFormat(myMeeting));
        holder.mMembersMeeting.setText(myMeeting.getMembersMeeting());
        Glide.with(holder.mImageMeeting.getContext())
                .load(myMeeting.getImagesMeeting())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mImageMeeting);


        holder.mDeleteMeeting.setOnClickListener(v -> mMyMeetingViewModel.removeMeeting(myMeeting));


    }

    @Override
    public int getItemCount() {
        return mMyMeetings.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        //UI components

        @BindView(R.id.list_meeting_imageview_meeting)
        ImageView mImageMeeting;

        @BindView(R.id.list_meeting_imagebutton_delete_meeting)
        ImageButton mDeleteMeeting;

        @BindView(R.id.list_meeting_textview_meeting_information)
        TextView mInformationMeeting;

        @BindView(R.id.list_meeting_textview_members_address_email)
        TextView mMembersMeeting;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);




        }
    }

}
