package com.prag.hashtags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    CollectionReference noteRef;
    Main_Views_Adapter main_views_adapter;

    @Override
    public void onStart() {
        super.onStart();
        main_views_adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        main_views_adapter.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        noteRef = db.collection("Texts");

        recyclerView = findViewById(R.id.mainRecycler);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(i);
            }
        });

        Query query = noteRef.orderBy("Text", Query.Direction.DESCENDING);

        Toast.makeText(getApplicationContext(), query.get().toString(),Toast.LENGTH_SHORT).show();
        FirestoreRecyclerOptions<Add_item_model> options = new FirestoreRecyclerOptions.Builder<Add_item_model>()
                .setQuery(query, Add_item_model.class)
                .build();

        main_views_adapter = new Main_Views_Adapter(options,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(main_views_adapter);
    }
}
