package com.uk.trigtracker;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.uk.trigtracker.Activities.MainActivity;
import com.uk.trigtracker.Fragments.InfoBoxFragment;
import com.uk.trigtracker.Fragments.MapFragment;
import com.uk.trigtracker.Fragments.VisitedMenuFragment;
import com.uk.trigtracker.Models.TrigPoint;

import java.util.ArrayList;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView titleText;
    SharedPreferences prefs;
    Gson gson;
    RecyclerViewAdapter viewAdapter;

    public RecyclerViewHolder(@NonNull final View itemView, final VisitedMenuFragment fragment) {
        // ItemView is recycle_row
        super(itemView);

        final ArrayList<Circle> allMarkers = fragment.getAllMarkers();

        prefs = itemView.getContext().getSharedPreferences("MyPref", 0);
        gson = new Gson();
        titleText = itemView.findViewById(R.id.title);

        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titleText.setTextColor(fragment.getResources().getColor(R.color.visited));
//                titleText.setBackgroundColor(fragment.getResources().getColor(R.color.visited));

                InfoBoxFragment infoBoxFragment = new InfoBoxFragment();

                Circle trigMarker = null;
                String test = titleText.getText().toString();
                TrigPoint trigPoint = null;

                for(Circle c : allMarkers) {
                    TrigPoint current = (TrigPoint)c.getTag();
                    if(current.getName().equals(test)) {
                        trigMarker = c;
                        trigPoint = current;
                        break;
                    }
                }

//                 Pass data to the info box fragment


                MapFragment mapFragment = fragment.getMapFragment();
                GoogleMap mGoogleMap = mapFragment.getmGoogleMap();

                Circle selectionIndicator = mGoogleMap.addCircle(new CircleOptions()
                        .center(new LatLng(trigPoint.getlatitude(), trigPoint.getLongitude()))
                        .clickable(false)
                        .radius(340)
                        .strokeColor(fragment.getResources().getColor(R.color.selectionIndicator))
                        .strokeWidth(12)
                        .fillColor(Color.TRANSPARENT));

                infoBoxFragment.setTrigPoint(trigPoint);
                infoBoxFragment.setCircle(trigMarker);
                infoBoxFragment.setSelectionIndicator(selectionIndicator);

                FragmentManager fm = ((MainActivity)itemView.getContext()).getSupportFragmentManager();
                fm.popBackStack();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.main_layout, infoBoxFragment, null).commit();

                LatLng cameraPos = trigMarker.getCenter();
                mapFragment.getmGoogleMap().animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPos, 12f));

            }
        });

    }

    public void setViewAdapter(RecyclerViewAdapter viewAdapter) {
        this.viewAdapter = viewAdapter;
    }
}
