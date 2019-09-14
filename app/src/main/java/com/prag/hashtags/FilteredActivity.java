package com.prag.hashtags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FilteredActivity extends AppCompatActivity {

    FilteredAdapter_Recycler filteredAdapter_recycler;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    CollectionReference noteRef;

    String find;

    @Override
    public void onStart() {
        super.onStart();
        filteredAdapter_recycler.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        filteredAdapter_recycler.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered);
        recyclerView = findViewById(R.id.filter_recycler);
        db = FirebaseFirestore.getInstance();
        noteRef = db.collection("Texts");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            assert extras != null;
            find = "#"+extras.getString("TAG");
            Toast.makeText(getApplicationContext(),find,Toast.LENGTH_SHORT).show();
        }


        //Query query = noteRef.startAfter(find);
        Query query = noteRef.whereLessThanOrEqualTo("Text",find);

        Toast.makeText(getApplicationContext(), query.get().toString(),Toast.LENGTH_SHORT).show();
        FirestoreRecyclerOptions<Add_item_model> options = new FirestoreRecyclerOptions.Builder<Add_item_model>()
                .setQuery(query, Add_item_model.class)
                .build();

        filteredAdapter_recycler = new FilteredAdapter_Recycler(options,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(filteredAdapter_recycler);

    }
}
