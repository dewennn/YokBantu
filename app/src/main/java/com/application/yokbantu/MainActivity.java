package com.application.yokbantu;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.application.yokbantu.database.FirebaseManager;
import com.application.yokbantu.fragments.Categories;
import com.application.yokbantu.fragments.Darurat;
import com.application.yokbantu.fragments.DonationCount;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        // FIREBASE AUTH
        FirebaseAuth auth = FirebaseManager.getFirebaseAuth();

        auth.signInWithEmailAndPassword("tes123@gmail.com", "tes123")
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign-in successful
                FirebaseUser user = auth.getCurrentUser();
                Toast.makeText(this, "Sign in succesful", Toast.LENGTH_SHORT).show();
            } else {
                // Handle errors
                Toast.makeText(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // FRAGMENT
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.donation_count_container, new DonationCount());
        transaction.replace(R.id.darurat, new Darurat());
        transaction.replace(R.id.categories, new Categories());

        transaction.commit();
    }
}