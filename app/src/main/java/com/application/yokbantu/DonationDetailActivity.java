package com.application.yokbantu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.application.yokbantu.objects.Donation;
import com.bumptech.glide.Glide;

public class DonationDetailActivity extends AppCompatActivity {
    Donation donation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail);

        // Get the Donation object from the intent
        Intent intent = getIntent();
        donation = intent.getParcelableExtra("donation");

        if (donation != null) {
            setDonationDetails(donation);
        }
    }

    private void setDonationDetails(Donation donation) {
        // Assign each field to the corresponding view

        // Title
        TextView titleView = findViewById(R.id.title);
        titleView.setText(donation.getTitle());

        // Description
        TextView descriptionView = findViewById(R.id.description);
        descriptionView.setText(donation.getDescription());

        // Donation Count
        TextView donationCountView = findViewById(R.id.donationCount);
        donationCountView.setText(String.valueOf(StaticFunctions.formatCashRp(donation.getDonationCount())));

        // Donation Target
        TextView donationTargetView = findViewById(R.id.donationTarget);
        String temp = "Terkumpul dari " + StaticFunctions.formatCashRp(donation.getDonationTarget());
        donationTargetView.setText(temp);

        // Total Donations
        TextView totalDonationView = findViewById(R.id.totalDonation);
        totalDonationView.setText(String.valueOf(donation.getTotalDonation()));

        // Total News
        TextView totalNewsView = findViewById(R.id.totalNews);
        totalNewsView.setText(String.valueOf(donation.getTotalNews()));

        // Total Expenses Used
        TextView totalExpensesUsedView = findViewById(R.id.totalExpensesUsed);
        totalExpensesUsedView.setText(String.valueOf(donation.getTotalExpensesUsed()));

        // Image
        ImageView imageView = findViewById(R.id.image);
        Glide.with(this)
                .load(donation.getThumbnailUrl()) // Assuming the image URL is stored in the `imageUrl` property
                .centerCrop()
                .into(imageView);
    }
}
