package com.uk.trigtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uk.trigtracker.Fragments.VisitedMenuFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<String> titleData;
    private VisitedMenuFragment fragment;

    public RecyclerViewAdapter(Context context, ArrayList<String> titles, VisitedMenuFragment fragment) {
        this.layoutInflater = LayoutInflater.from(context);
        this.titleData = titles;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.recycle_row, viewGroup, false);
        RecyclerViewHolder vHolder = new RecyclerViewHolder(view, fragment);
        vHolder.setViewAdapter(this);


        return vHolder;

//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        String title = titleData.get(i);

        recyclerViewHolder.titleText.setText(title);
    }

    @Override
    public int getItemCount() {
        return titleData.size();
    }

    public void setAllTitles(ArrayList<String> allTitles) {
        ArrayList<String> titles = (ArrayList)allTitles.clone();

        Collections.sort(titles, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        this.titleData = titles;
    }

    public ArrayList<String> getTitleData() {
        return this.titleData;
    }
}
