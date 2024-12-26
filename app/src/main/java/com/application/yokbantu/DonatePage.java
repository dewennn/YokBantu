package com.application.yokbantu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class DonatePage extends AppCompatActivity {

    private FirebaseFirestore db;
    private String documentId;  // Store the passed document ID
    private EditText amountInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_page);

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();

        // Retrieve the document ID passed from ActivityDetail
        documentId = getIntent().getStringExtra("id");

        // Initialize views
        amountInput = findViewById(R.id.amount_input);
        Button donateButton = findViewById(R.id.donate_button);

        // Set onClickListener for the donate button
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the amount from EditText
                String amountStr = amountInput.getText().toString();

                if (!amountStr.isEmpty()) {
                    int amount = Integer.parseInt(amountStr); // Parse the amount to integer

                    // Call method to update the Firestore document
                    updateDonationAmount(amount);
                } else {
                    Toast.makeText(DonatePage.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to update the donation amount in Firestore
    private void updateDonationAmount(int amount) {
        // Reference to the document in the "donations" collection using the passed document ID
        db.collection("donations").document(documentId)
        .update("donationCount", FieldValue.increment(amount)) // This will add the amount to the current donation count
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(DonatePage.this, "Donation updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DonatePage.this, "Error updating donation.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
