package com.prag.hashtags;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {

    EditText editText;
    HashTagHelper mTextHashTagHelper;
    Button submit;
    FirebaseFirestore db;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        FirebaseApp.initializeApp(this);
        submit = findViewById(R.id.submit);
        editText = findViewById(R.id.edit);
        progressBar = findViewById(R.id.progress);
        db = FirebaseFirestore.getInstance();
       /* mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.blue),
                new HashTagHelper.OnHashTagClickListener() {
                    @Override
                    public void onHashTagClicked(String hashTag) {
                    }
                });*/


        mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.blue), null);
        mTextHashTagHelper.handle(editText);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString().trim();
                List<String> allHashTags = mTextHashTagHelper.getAllHashTags();
                Map<String, Object> docData = new HashMap<>();
                docData.put("Text",text);
                docData.put("TagsArray", allHashTags);
                if (text!=null){
                    progressBar.setVisibility(View.VISIBLE);
                    FireBase_AddItem(docData);
                }else {
                    Toast.makeText(getApplicationContext(),"Please add something",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void FireBase_AddItem(Map<String, Object> item) {
        db.collection("Texts").document().set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Successfully added..", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
