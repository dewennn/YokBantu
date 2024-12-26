package com.application.yokbantu;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.application.yokbantu.fragments.Darurat;
import com.application.yokbantu.fragments.DonationCount;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        if (savedInstanceState == null) {
            DonationCount donationCount = new DonationCount();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.donation_count_container, donationCount)
                    .commit();

            Darurat darurat = new Darurat();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.darurat, darurat)
                    .commit();
        }
    }
}