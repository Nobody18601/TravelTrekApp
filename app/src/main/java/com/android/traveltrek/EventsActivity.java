package com.android.traveltrek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity implements EventsAdapter.ItemClickListener {

    EventsAdapter adapter;
    DatabaseReference dbRefNames;
    DatabaseReference dbRefTypes;
    DatabaseReference dbRefDesc;
    DatabaseReference dbRefTimes;
    DatabaseReference dbRefURLs;
    DatabaseReference dbRefLocations;
    ArrayList<String> eventNames = new ArrayList<>();
    ArrayList<String> eventTypes = new ArrayList<>();
    ArrayList<String> eventDesc = new ArrayList<>();
    ArrayList<String> eventTimes = new ArrayList<>();
    ArrayList<String> eventURLs = new ArrayList<>();
    ArrayList<String> eventLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.events);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new EventsAdapter(getApplicationContext(), eventNames, eventDesc, eventLocations, eventTimes, eventTypes, eventURLs);
        adapter.setClickListener(this);

        Intent getResults = getIntent();
        String country = getResults.getStringExtra("Country");

        dbRefNames =  FirebaseDatabase.getInstance().getReference("Countries/" + country+ "/names");
        dbRefNames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eventNames.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    eventNames.add(ds.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbRefTypes =  FirebaseDatabase.getInstance().getReference("Countries/" + country+ "/type");
        dbRefTypes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eventTypes.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    eventTypes.add(ds.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbRefDesc =  FirebaseDatabase.getInstance().getReference("Countries/" + country+ "/desc");
        dbRefDesc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eventDesc.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    eventDesc.add(ds.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbRefTimes =  FirebaseDatabase.getInstance().getReference("Countries/" + country+ "/time");
        dbRefTimes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eventTimes.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    eventTimes.add(ds.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbRefLocations =  FirebaseDatabase.getInstance().getReference("Countries/" + country+ "/locations");
        dbRefLocations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eventLocations.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    eventLocations.add(ds.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dbRefURLs =  FirebaseDatabase.getInstance().getReference("Countries/" + country+ "/img_url");
        dbRefURLs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eventURLs.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    eventURLs.add(ds.getValue(String.class));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {}
}