package com.uk.trigtracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView titleText;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        titleText = itemView.findViewById(R.id.title);
    }
}
