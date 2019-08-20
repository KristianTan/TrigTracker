package com.uk.trigtracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uk.trigtracker.Activities.MainActivity;
import com.uk.trigtracker.Fragments.InfoBoxFragment;
import com.uk.trigtracker.Models.TrigPoint;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView titleText;
    SharedPreferences prefs;
    Gson gson;

    public RecyclerViewHolder(@NonNull final View itemView) {
        super(itemView);

        prefs = itemView.getContext().getSharedPreferences("MyPref", 0);
        gson = new Gson();
        titleText = itemView.findViewById(R.id.title);

        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = prefs.getString(titleText.getText().toString(), "");
                TrigPoint trigPoint = gson.fromJson(json, TrigPoint.class);

//                Toast.makeText(itemView.getContext(), trigPoint.getName(), Toast.LENGTH_SHORT);
                InfoBoxFragment infoBoxFragment = new InfoBoxFragment();

                // Pass data to the info box fragment
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("data", trigPoint);
//                infoBoxFragment.setArguments(bundle);
//                infoBoxFragment.setCircle(circle);
//
//                FragmentTransaction fragmentTransaction = ((MainActivity)itemView.getContext()).getSupportFragmentManager().beginTransaction();
//
////                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.add(R.id.main_layout, infoBoxFragment, null).commit();

            }
        });
    }
}
