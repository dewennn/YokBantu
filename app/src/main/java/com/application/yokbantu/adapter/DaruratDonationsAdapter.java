package com.application.yokbantu.adapter;

import static com.application.yokbantu.StaticFunctions.formatCashRp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.yokbantu.ActivityDetail;
import com.application.yokbantu.R;
import com.application.yokbantu.objects.Donation;

import java.text.DecimalFormat;
import java.util.List;

public class DaruratDonationsAdapter extends RecyclerView.Adapter<DaruratDonationsAdapter.ViewHolder> {
    private Context context;
    private List<Donation> donations;

    public DaruratDonationsAdapter(Context context, List<Donation> donations) {
        this.context = context;
        this.donations = donations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.component_donation_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get current donation
        Donation donation = donations.get(position);

        // Bind data to views
        String titleText = donation.getTitle();

        if (titleText.length() > 50) {
            titleText = titleText.substring(0, 50) + "...";  // Trim to 50 characters and add ellipsis
        }

        holder.title.setText(titleText);
        holder.donationCount.setText(formatCashRp(donation.getDonationCount()));

        holder.itemView.setOnClickListener(v -> {
            Intent openDetail = new Intent(context, ActivityDetail.class);

            openDetail.putExtra("id", donation.getId());
            openDetail.putExtra("description", donation.getDescription());
            openDetail.putExtra("donationCount", donation.getDonationCount());
            openDetail.putExtra("donationTarget", donation.getDonationTarget());
            openDetail.putExtra("title", donation.getTitle());
            openDetail.putExtra("totalDonation", donation.getTotalDonation());
            openDetail.putExtra("totalExpensesUsed", donation.getTotalExpensesUsed());
            openDetail.putExtra("totalNews", donation.getTotalNews());

            context.startActivity(openDetail);
        });
    }

    @Override
    public int getItemCount() {
        // Return the total number of items
        return donations.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, donationCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            donationCount = itemView.findViewById(R.id.donationCount);
        }
    }
}
