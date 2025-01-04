package com.application.yokbantu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.yokbantu.DonationDetailActivity;
import com.application.yokbantu.R;
import com.application.yokbantu.StaticFunctions;
import com.application.yokbantu.objects.Donation;
import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.ViewHolder> {
    private Context context;
    private List<Donation> donations;

    public DonationsAdapter(Context context, List<Donation> donations) {
        this.context = context;
        this.donations = donations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.component_donation_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get current donation
        Donation donation = donations.get(position);

        // Bind data to views
        //-- Title
        String titleText = donation.getTitle();
        if (titleText.length() > 50) { titleText = titleText.substring(0, 50) + "...";}
        holder.title.setText(titleText);

        //-- CurrDonationCount
        holder.donationCount.setText(StaticFunctions.formatCashRp(donation.getDonationCount()));

        //-- Image Link
        Glide.with(context).load(donation.getThumbnailUrl()).centerCrop().into(holder.thumbnail);

        //-- If donation have occurred date
        if(donation.getOccurredAt() != null){
            // Calculate the difference between today and occur date in milliseconds, then covert to days
            Date today = new Date();
            long diffInMillis = today.getTime() - donation.getOccurredAt().getTime();
            Long daysElapse = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            String temp = daysElapse + " hari yang lalu";


            holder.popup.setText(temp);
            holder.popupContainer.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent openDetail = new Intent(context, DonationDetailActivity.class);
            openDetail.putExtra("donation", donation);
            context.startActivity(openDetail);
        });
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, donationCount, popup;
        ImageView thumbnail;
        LinearLayout popupContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            popup = itemView.findViewById(R.id.popup);
            donationCount = itemView.findViewById(R.id.donationCount);
            thumbnail = itemView.findViewById(R.id.donationThumbnail);
            popupContainer = itemView.findViewById(R.id.popup_container);
        }
    }
}
