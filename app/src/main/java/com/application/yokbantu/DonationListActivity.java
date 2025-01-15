package com.application.yokbantu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.yokbantu.adapter.DonationsListAdapter;
import com.application.yokbantu.database.FirebaseManager;
import com.application.yokbantu.objects.Donation;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DonationListActivity extends AppCompatActivity {

    private List<Donation> donationsList = new ArrayList<>();
    private DonationsListAdapter adapter;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_list);

        Intent search = getIntent();
        searchQuery = search.getStringExtra("search_query");
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQuery(searchQuery, false);


        adapter = new DonationsListAdapter(this, donationsList);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewDonations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        fetchDataFromFirestore();
    }

    private Integer getIntegerFromMap(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof Long) {
            return ((Long) value).intValue();
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return null;
        }
    }

    private void fetchDataFromFirestore() {
        FirebaseFirestore firestore = FirebaseManager.getFirebaseFirestore();

        firestore.collection("donations")
        .limit(5) // Limit to top 5 results
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot snapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("LISTENER ERROR", "Listen failed.", e);
                    return;
                }

                donationsList.clear();

                for (QueryDocumentSnapshot document : snapshot) {
                    String id = document.getId();

                    Map<String, Object> data = document.getData();

                    String description = (String) data.get("description");
                    Integer donationCount = getIntegerFromMap(data, "donationCount");
                    Integer donationTarget = getIntegerFromMap(data, "donationTarget");
                    String title = (String) data.get("title");
                    Integer totalDonation = getIntegerFromMap(data, "totalDonation");
                    Integer totalExpensesUsed = getIntegerFromMap(data, "totalExpensesUsed");
                    Integer totalNews = getIntegerFromMap(data, "totalNews");
                    String thumbnailUrl = (String) data.get("thumbnailUrl");
                    Timestamp occuredAtTimestamp = (Timestamp) data.get("occurredAt");
                    Date occurredAt = occuredAtTimestamp != null ? occuredAtTimestamp.toDate() : null;

                    donationsList.add(new Donation(
                            id,
                            description,
                            donationCount,
                            donationTarget,
                            title,
                            totalDonation,
                            totalExpensesUsed,
                            totalNews,
                            thumbnailUrl,
                            occurredAt
                    ));
                }

                adapter.notifyDataSetChanged();
            }
        });
    }
}