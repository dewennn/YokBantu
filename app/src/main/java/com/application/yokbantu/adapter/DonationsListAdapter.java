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

public class DonationsListAdapter extends RecyclerView.Adapter<DonationsListAdapter.ViewHolder> {
    private Context context;
    private List<Donation> donations;

    public DonationsListAdapter(Context context, List<Donation> donations) {
        this.context = context;
        this.donations = donations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.component_donation_list, parent, false);
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
        TextView title, donationCount;
        ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            donationCount = itemView.findViewById(R.id.donationCount);
            thumbnail = itemView.findViewById(R.id.donationThumbnail);
        }
    }
}
