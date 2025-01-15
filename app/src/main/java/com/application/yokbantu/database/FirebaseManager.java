package com.application.yokbantu.database;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class FirebaseManager {

    private static FirebaseAuth auth;
    private static FirebaseFirestore db;

    public static FirebaseAuth getFirebaseAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static FirebaseFirestore getFirebaseFirestore() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }
        return db;
    }

    public static <E> void create(String collection, E obj){
        db.collection(collection)
        .add(obj)
        .addOnSuccessListener(documentReference -> {
            Log.d("FIREBASE", "DocumentSnapshot added with ID: " + documentReference.getId());
        })
        .addOnFailureListener(e -> {
            Log.w("FIREBASE", "Error adding document", e);
        });
    }

    public static <E> void put(String key, E value){
        DocumentReference docRef = db.collection("users").document("documentId");

        docRef.update(key, value)
        .addOnSuccessListener(aVoid -> {
            Log.d("FIREBASE", "DocumentSnapshot successfully updated!");
        })
        .addOnFailureListener(e -> {
            Log.w("FIREBASE", "Error updating document", e);
        });
    }

    public static void delete(String collection, String id){
        db.collection(collection).document(id)
        .delete()
        .addOnSuccessListener(aVoid -> {
            Log.d("FIREBASE", "DocumentSnapshot successfully deleted!");
        })
        .addOnFailureListener(e -> {
            Log.w("FIREBASE", "Error deleting document", e);
        });
    }

}
