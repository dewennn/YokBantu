package com.application.yokbantu;

import static com.application.yokbantu.StaticFunctions.formatCashRp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class ActivityDetail extends AppCompatActivity {

    // Declare variables to hold the passed data
    private String id;
    private String description;
    private int donationCount;
    private int donationTarget;
    private String title;
    private int totalDonation;
    private int totalExpensesUsed;
    private int totalNews;

    // Firebase Firestore instance
    private FirebaseFirestore db;

    // Views
    private TextView donationCountTextView;
    private TextView donationTargetTextView;
    private TextView totalDonationTextView;
    private TextView totalExpensesTextView;
    private TextView totalNewsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize Firebase Firestore instance
        db = FirebaseFirestore.getInstance();

        // Retrieve the data passed via Intent
        id = getIntent().getStringExtra("id");
        description = getIntent().getStringExtra("description");
        donationCount = getIntent().getIntExtra("donationCount", 0);  // Default value 0
        donationTarget = getIntent().getIntExtra("donationTarget", 0);
        title = getIntent().getStringExtra("title");
        totalDonation = getIntent().getIntExtra("totalDonation", 0);
        totalExpensesUsed = getIntent().getIntExtra("totalExpensesUsed", 0);
        totalNews = getIntent().getIntExtra("totalNews", 0);

        // Initialize views
        donationCountTextView = findViewById(R.id.donationCount);
        donationTargetTextView = findViewById(R.id.donationTarget);
        totalDonationTextView = findViewById(R.id.totalDonation);
        totalExpensesTextView = findViewById(R.id.totalExpenses);
        totalNewsTextView = findViewById(R.id.totalNews);
        TextView titleTextView = findViewById(R.id.title);
        TextView descriptionTextView = findViewById(R.id.description);

        // Set the data to the views
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        donationCountTextView.setText(formatCashRp(donationCount));
        String temp = "Terkumpul dari " + formatCashRp(donationTarget);
        donationTargetTextView.setText(temp);
        totalDonationTextView.setText(String.valueOf(totalDonation));
        totalExpensesTextView.setText(String.valueOf(totalExpensesUsed));
        totalNewsTextView.setText(String.valueOf(totalNews));

        Button donateButton = findViewById(R.id.donate_button);

        // Set an OnClickListener to the button
        donateButton.setOnClickListener(v -> goToDonate());

        // Set up Firestore listener to listen for changes in the donation document
        setupRealTimeListener();
    }

    private void setupRealTimeListener() {
        // Listen for changes in the specific donation document
        db.collection("donations").document(id)
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) {
                        Toast.makeText(ActivityDetail.this, "Error loading data", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        // Get updated donation data from Firestore
                        donationCount = documentSnapshot.getLong("donationCount").intValue();
                        donationTarget = documentSnapshot.getLong("donationTarget").intValue();
                        totalDonation = documentSnapshot.getLong("totalDonation").intValue();
                        totalExpensesUsed = documentSnapshot.getLong("totalExpensesUsed").intValue();
                        totalNews = documentSnapshot.getLong("totalNews").intValue();

                        // Update the views with the new data
                        donationCountTextView.setText(formatCashRp(donationCount));
                        String targetText = "Terkumpul dari " + formatCashRp(donationTarget);
                        donationTargetTextView.setText(targetText);
                        totalDonationTextView.setText(String.valueOf(totalDonation));
                        totalExpensesTextView.setText(String.valueOf(totalExpensesUsed));
                        totalNewsTextView.setText(String.valueOf(totalNews));
                    }
                });
    }

    private void goToDonate() {
        Intent donate = new Intent(ActivityDetail.this, DonatePage.class);
        donate.putExtra("id", id);
        startActivity(donate);
    }
}
