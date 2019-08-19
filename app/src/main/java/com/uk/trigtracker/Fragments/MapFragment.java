package com.uk.trigtracker.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import java.util.Map;
import java.util.Random;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.map_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = getContext().getSharedPreferences("MyPref", 0);
        editor = prefs.edit();

        mMapView = mView.findViewById(R.id.map);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng cameraPos = new LatLng(53.3499986, -1.83333);

        ArrayList<TrigPoint> points = readFromCsv();

        for (TrigPoint t : points) {
            // Add circles to the map

            int fillColor = Color.TRANSPARENT;

            if(t.getVisited()) {
                fillColor = Color.parseColor("#e63c60");
            }

            Circle c = mGoogleMap.addCircle(new CircleOptions()
                    .center(new LatLng(t.getlatitude(), t.getLongitude()))
                    .clickable(true)
                    .radius(300)
                    .strokeColor(Color.BLACK)
                    .strokeWidth(5)
                    .fillColor(fillColor)
            );

            // Store corresponding TrigPoint object in the tag of the circle
            c.setTag(t);

            mGoogleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle) {
                    TrigPoint tag = (TrigPoint) circle.getTag();

                    LatLng cameraPos = new LatLng(tag.getlatitude(), tag.getLongitude());
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPos, 12f));

                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();

                    InfoBoxFragment infoBoxFragment = new InfoBoxFragment();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data", tag);
                    infoBoxFragment.setArguments(bundle);

                    infoBoxFragment.setCircle(circle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.main_layout, infoBoxFragment)
                            .addToBackStack("")
                            .commit();
                }
            });

        }

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPos, 6.5f));

    }

    public ArrayList<TrigPoint> readFromCsv() {
        InputStream is = getResources().openRawResource(R.raw.np_trigs);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is,
                Charset.forName("UTF-8")));


//        Map<String,?> keys = prefs.getAll();
//
//        for(Map.Entry<String,?> entry : keys.entrySet()){
//            System.out.println(entry.getKey());
//        }

        ArrayList<TrigPoint> points = new ArrayList<>();

        String line;
        try {
            // Step over headers
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                Boolean visited = false;
                if(prefs.contains(tokens[3])) {
                        visited = true;
                }

                TrigPoint trigPoint = new TrigPoint(tokens, visited);
                points.add(trigPoint);
            }
        } catch (IOException e) {
            Log.wtf("MapFragment", "Error reading file at line 87: " + e);
            e.printStackTrace();
        }



        return points;


    }

}
