package com.uk.trigtracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.uk.trigtracker.Models.TrigPoint;
import com.uk.trigtracker.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.map_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = mView.findViewById(R.id.map);

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

        ArrayList<TrigPoint> points = readFromCsv();

        for(TrigPoint t : points) {
            // Add circles to the map
            Circle c = googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(t.getlatitude(), t.getLongitude()))
                    .clickable(true)
                    .radius(300)
                    .strokeColor(Color.BLACK)
                    .strokeWidth(5)
                    .fillColor(Color.TRANSPARENT)
            );

            // Store corresponding TrigPoint object in the tag of the circle
            c.setTag(t);

            googleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle) {
                    if(circle.getFillColor() == Color.RED) {
                        circle.setFillColor(Color.TRANSPARENT);
                    } else {
                        circle.setFillColor(Color.RED);
                    }

                    TrigPoint tag = (TrigPoint)circle.getTag();

                    Toast.makeText(getActivity(), tag.getName(),
                            Toast.LENGTH_LONG).show();

                    InfoBoxFragment infoBoxFragment = new InfoBoxFragment();

                    // TODO: Set contents of text views in infoBoxFragment
//                    TextView name = getView().findViewById(R.id.info_name);
//                    name.setText(tag.getName());

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.main_layout, infoBoxFragment)
                            .addToBackStack("")
                            .commit();
                }
            });

        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 9.7f));

    }

    public ArrayList<TrigPoint> readFromCsv() {

        InputStream is = getResources().openRawResource(R.raw.peaks_trigs);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is,
                Charset.forName("UTF-8")));

        ArrayList<TrigPoint> points = new ArrayList<>();

        String line;
        try {
            // Step over headers
            reader.readLine();

            while((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                points.add(new TrigPoint(tokens));
            }
        } catch (IOException e) {
            Log.wtf("MapFragment", "Error reading file at line 87: " + e);
            e.printStackTrace();
        }

        return points;


    }
}
