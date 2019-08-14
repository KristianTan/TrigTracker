package com.uk.trigtracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opencsv.CSVReader;
import com.uk.trigtracker.Models.TrigPoint;
import com.uk.trigtracker.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class FirstFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    public FirstFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.first_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView)mView.findViewById(R.id.map);

        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng marker = new LatLng(53.3499986, -1.83333);

        InputStream is = getResources().openRawResource(R.raw.peaks_trigs);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is,
                Charset.forName("UTF-8")));

        ArrayList<String[]> lines = new ArrayList<>();

        String line;
        try {

            // Step over headers
            reader.readLine();

            while((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                // TODO: Add to arraylist of TrigPoint objects instead of String[]
                lines.add(tokens);
            }
        } catch (IOException e) {
            Log.wtf("MainActivity", "Error reading file at line 31: " + e);
            e.printStackTrace();
        }

        for(String[] i : lines) {
            CircleOptions c = new CircleOptions()
                    .center(new LatLng(Double.parseDouble(i[0]), Double.parseDouble(i[1])))
                    .clickable(true)
                    .radius(300)
                    .strokeColor(Color.BLACK)
                    .strokeWidth(5)
                    .fillColor(Color.TRANSPARENT);

            googleMap.addCircle(c);

            googleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle) {
                    Toast.makeText(getActivity(), circle.getCenter().toString(),
                            Toast.LENGTH_LONG).show();
                    if(circle.getFillColor() == Color.RED) {
                        circle.setFillColor(Color.TRANSPARENT);
                    } else {
                        circle.setFillColor(Color.RED);
                    }

                    // TODO: Get TrigPoint data in click listener.  Maybe TrigPoint has onClick() ??
                }
            });
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 9.7f));
    }
}
