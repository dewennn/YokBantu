package com.application.yokbantu.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.yokbantu.R;
import com.application.yokbantu.adapter.CategoriesAdapter;
import com.application.yokbantu.adapter.DonationsAdapter;
import com.application.yokbantu.database.FirebaseManager;
import com.application.yokbantu.objects.Donation;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Categories extends Fragment {

    private CategoriesAdapter categoryAdapter;
    private List<String> categoryName;      // List of categories name
    private List<Integer> categoryImg;      // List of categories icon
    private Integer choice;                 // Indicate the currently active category

    private List<Donation> donationsList;
    private DonationsAdapter donationAdapter;

    public Categories() {}

    public static Categories newInstance() {
        return new Categories();
    }

    private void setChoice(int position){
        this.choice = position;
        fetchDataFromFirestore();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.choice = 0;
        donationsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Prepare View & Data
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        prepareData();
        fetchDataFromFirestore();

        // Create the adapter
        donationAdapter = new DonationsAdapter(getContext(), donationsList);
        categoryAdapter = new CategoriesAdapter(getContext(), categoryName, categoryImg, choice, this::setChoice);

        // Populate Categories
        RecyclerView categories = view.findViewById(R.id.recyclerViewCategories);
        LinearLayoutManager layoutManagerCategories = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categories.setLayoutManager(layoutManagerCategories);
        categories.setAdapter(categoryAdapter);

        // Populate Donations
        RecyclerView donations = view.findViewById(R.id.recyclerViewDonations);
        LinearLayoutManager layoutManagerDonation = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        donations.setLayoutManager(layoutManagerDonation);
        donations.setAdapter(donationAdapter);

        // Return final view
        return view;
    }

    private void prepareData() {
        categoryName = new ArrayList<>();
        categoryImg = new ArrayList<>();

        // Add sample data
        categoryName.add("Pendidikan");
        categoryImg.add(R.drawable.icon_books); // Replace with your drawable resource

        categoryName.add("Kesehatan");
        categoryImg.add(R.drawable.icon_health);

        categoryName.add("Satwa");
        categoryImg.add(R.drawable.icon_animals);

        categoryName.add("Lingkungan");
        categoryImg.add(R.drawable.icon_environment);
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
        .where(Filter.equalTo("category", categoryName.get(this.choice)))
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

                    donationsList.add(new Donation(
                            id,
                            description,
                            donationCount,
                            donationTarget,
                            title,
                            totalDonation,
                            totalExpensesUsed,
                            totalNews,
                            thumbnailUrl
                    ));
                }

                donationAdapter.notifyDataSetChanged();
            }
        });
    }
}