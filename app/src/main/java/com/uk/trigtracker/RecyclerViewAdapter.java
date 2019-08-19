package com.uk.trigtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private LayoutInflater layoutInflater;
    private List<String> titleData;

    public RecyclerViewAdapter(Context context, List<String> titles) {
        this.layoutInflater = LayoutInflater.from(context);
        this.titleData = titles;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.recycle_row, viewGroup, false);
        RecyclerViewHolder vHolder = new RecyclerViewHolder(view);

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

    public void addItem(String title) {
        titleData.add(title);
    }
}
