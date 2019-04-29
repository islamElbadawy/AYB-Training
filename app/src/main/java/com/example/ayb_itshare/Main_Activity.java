package com.example.ayb_itshare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main_Activity extends AppCompatActivity {

    private Button get_data;
    private ListView posts;
    ArrayList<Model> arrayList;
    Adapter adapter;

    FirebaseDatabase database;
    DatabaseReference Posts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        get_data = findViewById(R.id.get_data);
        posts = findViewById(R.id.posts);
        posts = findViewById(R.id.posts);


        database = FirebaseDatabase.getInstance();
        Posts = database.getReference("posts");


    }

    public void  getData(View view){

        Posts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String post_image= ds.child("Photo").getValue().toString();
                    String post_description= ds.child("Post").getValue().toString();
                    String post_user= ds.child("User").getValue().toString();

                    arrayList.add(new Model(post_image,post_description,post_user));

                }

                adapter = new  Adapter(getApplicationContext(),arrayList);
                posts.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
