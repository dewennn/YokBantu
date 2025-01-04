package com.application.yokbantu.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.yokbantu.R;
import com.application.yokbantu.adapter.DonationsAdapter;
import com.application.yokbantu.database.FirebaseManager;
import com.application.yokbantu.objects.Donation;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Darurat extends Fragment {

    private List<Donation> donationsList;
    private DonationsAdapter adapter;

    public Darurat() {}

    public static Darurat newInstance() {
        return new Darurat();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        donationsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_darurat, container, false);

        adapter = new DonationsAdapter(getContext(), donationsList);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewDonations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        fetchDataFromFirestore();

        return view;
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
        .where(Filter.equalTo("darurat", true))
        .addSnapshotListener
        (new EventListener<QuerySnapshot>() {
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
