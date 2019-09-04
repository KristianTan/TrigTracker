package com.uk.trigtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.uk.trigtracker.Activities.MainActivity;
import com.uk.trigtracker.Fragments.InfoBoxFragment;
import com.uk.trigtracker.Fragments.MapFragment;
import com.uk.trigtracker.Fragments.VisitedMenuFragment;
import com.uk.trigtracker.Models.TrigPoint;

import java.util.ArrayList;
import java.util.Map;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView titleText;
    SharedPreferences prefs;
    Gson gson;
    private VisitedMenuFragment fragment;

    public RecyclerViewHolder(@NonNull final View itemView, final VisitedMenuFragment fragment) {
        // ItemView is recycle_row
        super(itemView);
        this.fragment = fragment;

        final ArrayList<Circle> allMarkers = fragment.getAllMarkers();

        prefs = itemView.getContext().getSharedPreferences("MyPref", 0);
        gson = new Gson();
        titleText = itemView.findViewById(R.id.title);

        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = prefs.getString(titleText.getText().toString(), "");
                TrigPoint trigPoint = gson.fromJson(json, TrigPoint.class);

//                Toast.makeText(itemView.getContext(), "a", Toast.LENGTH_SHORT);
                InfoBoxFragment infoBoxFragment = new InfoBoxFragment();

                LatLng center = new LatLng(trigPoint.getlatitude(), trigPoint.getLongitude());
                Circle trigMarker = null;
                for(Circle c : allMarkers) {
//                    LatLng t = c.getCenter();
                    if(center.equals(c.getCenter())) {
//                        System.out.println("a");
                        trigMarker = c;
                        break;
                    }
                }

//                 Pass data to the info box fragment
                infoBoxFragment.setTrigPoint(trigPoint);
                infoBoxFragment.setCircle(trigMarker);

                FragmentManager fm = ((MainActivity)itemView.getContext()).getSupportFragmentManager();
                fm.popBackStack();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.main_layout, infoBoxFragment, null).commit();

                MapFragment mapFragment = fragment.getMapFragment();
                LatLng cameraPos = trigMarker.getCenter();
                mapFragment.getmGoogleMap().animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPos, 12f));

            }
        });
    }
}
