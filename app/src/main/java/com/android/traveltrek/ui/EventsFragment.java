package com.android.traveltrek.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.traveltrek.MainEventsAdapter;
import com.android.traveltrek.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class EventsFragment extends Fragment {

    RecyclerView dataList;
    List<String> titles = new ArrayList<>();
    List<String> images = new ArrayList<>();
    MainEventsAdapter mainEventsAdapter;
    DatabaseReference mDbRef;
    DatabaseReference imageRef;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);
        dataList = view.findViewById(R.id.datalist);

        mainEventsAdapter = new MainEventsAdapter(getContext(),titles,images);

        mDbRef =  FirebaseDatabase.getInstance().getReference("Countries_list");
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    titles.add(ds.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        imageRef =  FirebaseDatabase.getInstance().getReference("Countries_imgList");
        imageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot ds1: snapshot.getChildren()){
                    images.add(ds1.getValue(String.class));
                }
                mainEventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(mainEventsAdapter);

        return view;
    }

}