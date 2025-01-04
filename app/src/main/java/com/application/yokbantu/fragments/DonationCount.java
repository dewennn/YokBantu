package com.application.yokbantu.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.application.yokbantu.R;
import com.application.yokbantu.StaticFunctions;
import com.application.yokbantu.database.FirebaseManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.Locale;

public class DonationCount extends Fragment {

    public static DonationCount newInstance() {
        DonationCount fragment = new DonationCount();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // GET USER
        FirebaseAuth auth = FirebaseManager.getFirebaseAuth();
        FirebaseUser user = auth.getCurrentUser();

        // TARGET THE VIEWS
        View temp = inflater.inflate(R.layout.fragment_donation_count, container, false);
        TextView donationAmount = temp.findViewById(R.id.donationAmount);
        TextView streak = temp.findViewById(R.id.streak);

        // IF USER IS VALID, FILL VIEW WITH USER'S DATA IF NOT DISPLAY
        if(user != null){
            String userId = user.getUid();

            FirebaseFirestore firestore = FirebaseManager.getFirebaseFirestore();
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Extract data from the document as numbers
                        Long donation = documentSnapshot.getLong("donationAmount");
                        Long userStreak = documentSnapshot.getLong("streak");

                        // Format Donation
                        String formattedDonation = donation != null
                                ? StaticFunctions.formatCashRp(donation.intValue())
                                : "Rp 0";

                        // Set the values to the TextViews
                        donationAmount.setText(formattedDonation);
                        streak.setText(userStreak != null ? String.valueOf(userStreak) : "0");
                    } else {
                        donationAmount.setText("0");
                        streak.setText("0");
                    }
                }
            );
        }
        else {
            donationAmount.setText("0");
            streak.setText("0");
        }

        return temp;
    }
}