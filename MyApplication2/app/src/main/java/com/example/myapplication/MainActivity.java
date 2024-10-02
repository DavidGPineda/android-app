package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView userListView;
    private ArrayList<String> userList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userListView = findViewById(R.id.user_list);
        userList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        loadUsers();
    }

    private void loadUsers() {
        db.collection("usuarios").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String username = document.getString("username");
                    userList.add(username);
                }
                // Actualizar el ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
                userListView.setAdapter(adapter);
            } else {
                Log.d("FirestoreError", "Error getting documents: ", task.getException());
            }
        });
    }
}
