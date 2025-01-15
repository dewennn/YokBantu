package com.application.yokbantu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.application.yokbantu.database.FirebaseManager;
import com.application.yokbantu.objects.Donation;
import com.bumptech.glide.Glide;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class PaymentActivity extends AppCompatActivity {

    Donation donation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Get the Donation object from the intent
        Intent intent = getIntent();
        donation = intent.getParcelableExtra("donation");

        if (donation != null) {
            setDonationDetails(donation);
        }

        // Donation Handler
        TextView confirmation = findViewById(R.id.confirmationButton);
        EditText donationAmount = findViewById(R.id.inputDonation);
        CheckBox snk = findViewById(R.id.checkboxSnk);

        confirmation.setOnClickListener(v -> {
            String input = donationAmount.getText().toString();

            if(!snk.isChecked()){
                Toast.makeText(this, "setujui dulu s&k sebelum melanjutkan", Toast.LENGTH_SHORT).show();
            }
            else if (input.isEmpty()) {
                Toast.makeText(this, "nominal donasi belum di-isi", Toast.LENGTH_SHORT).show();
            }
            else if(Integer.parseInt(input) < 1000) {
                Toast.makeText(this, "nominal donasi minimal Rp 1,000.00", Toast.LENGTH_SHORT).show();
            }
            else {
                int temp = donation.getDonationCount() + Integer.parseInt(input);

                updateUserDonationTotal();
                FirebaseManager.put("donations", donation.getId(), "donationCount", temp);

                Toast.makeText(this, "terima kasih atas donasi anda!", Toast.LENGTH_SHORT).show();
                donationAmount.setText("");
            }
        });
    }

    private void setDonationDetails(Donation donation) {
        // Title
        TextView donationName = findViewById(R.id.donationName);
        donationName.setText(donation.getTitle());

        // Image
        ImageView imageView = findViewById(R.id.previewImage);
        Glide.with(this)
                .load(donation.getThumbnailUrl())
                .centerCrop()
                .into(imageView);
    }

    private void updateUserDonationTotal() {
        final int[] donationTotal = {0};

        String userId = FirebaseManager.getFirebaseAuth().getUid();

        FirebaseManager.getFirebaseFirestore()
        .collection("users") // Specify the collection
        .document(userId)
        .get()
        .addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {

                if (documentSnapshot.contains("donationAmount")) {
                    donationTotal[0] = documentSnapshot.getLong("donationAmount").intValue();
                }
            } else {

                Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(e -> {
            Toast.makeText(this, "Error fetching user donation total", Toast.LENGTH_SHORT).show();
        });

        FirebaseManager.put(
            "users",
            FirebaseManager.getFirebaseAuth().getUid(),
            "donationAmount",
            donationTotal[0]
        );
    }

}