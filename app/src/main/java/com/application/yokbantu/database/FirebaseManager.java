package com.application.yokbantu.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseManager {

    private static FirebaseAuth authInstance;
    private static FirebaseFirestore firestoreInstance;

    public static FirebaseAuth getFirebaseAuth() {
        if (authInstance == null) {
            authInstance = FirebaseAuth.getInstance();
        }
        return authInstance;
    }

    public static FirebaseFirestore getFirebaseFirestore() {
        if (firestoreInstance == null) {
            firestoreInstance = FirebaseFirestore.getInstance();
        }
        return firestoreInstance;
    }

}
