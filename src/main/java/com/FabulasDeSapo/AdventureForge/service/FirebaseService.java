package com.FabulasDeSapo.AdventureForge.service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    public void saveData(String collection, String document, Object data) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            db.collection(collection).document(document).set(data).get();  // Asegúrate de usar .get() para manejar la llamada asincrónica
        } catch (Exception e) {
            e.printStackTrace();  // Log para ver si algo salió mal
        }
    }

    public Object getData(String collection, String document) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        return db.collection(collection).document(document).get().get().getData();
    }
}