package com.uk.trigtracker.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.uk.trigtracker.Models.TrigPoint;
import com.uk.trigtracker.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ArrayList<Circle> allMarkers;
    ArrayList<TrigPoint> points;

    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        prefs = getContext().getSharedPreferences("MyPref", 0);
        editor = prefs.edit();
        points = readFromCsv();

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
        allMarkers = new ArrayList<>();

        mGoogleMap = googleMap;

        mGoogleMap.getUiSettings().setCompassEnabled(false);

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        final ArrayList<String> allTitles = new ArrayList<>();

        for (TrigPoint t : points) {
            // Add circles to the map

            int fillColor = Color.TRANSPARENT;

            if(t.getVisited()) {
                fillColor = getResources().getColor(R.color.visited);
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
            allMarkers.add(c);
            allTitles.add(t.getName());

            mGoogleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle) {
                    TrigPoint tag = (TrigPoint) circle.getTag();

                    Circle selectionIndicator = mGoogleMap.addCircle(new CircleOptions()
                                    .center(new LatLng(tag.getlatitude(), tag.getLongitude()))
                                    .clickable(false)
                                    .radius(340)
                                    .strokeColor(Color.WHITE)
                                    .strokeWidth(12)
                                    .fillColor(Color.TRANSPARENT));

                    LatLng cameraPos = new LatLng(tag.getlatitude(), tag.getLongitude());
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPos, 12f));

                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();

                    InfoBoxFragment infoBoxFragment = new InfoBoxFragment();

                    // Pass data to the info box fragment
                    infoBoxFragment.setTrigPoint(tag);
                    infoBoxFragment.setCircle(circle);
                    infoBoxFragment.setSelectionIndicator(selectionIndicator);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.add(R.id.main_layout, infoBoxFragment, null).commit();

                }
            });

        }

        ImageButton menuButton = getActivity().findViewById(R.id.menuButton);
        final MapFragment mapFragment = this;
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VisitedMenuFragment visitedMenuFragment = new VisitedMenuFragment();
                visitedMenuFragment.setAllMarkers(allMarkers);
                visitedMenuFragment.setMapFragment(mapFragment);
                visitedMenuFragment.setAllTrigNames(allTitles);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();

                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.main_layout, visitedMenuFragment, null).commit();
            }
        });

        final LatLng southWestBound = new LatLng(49.234298, -5.970560);
        final LatLng northEastBound = new LatLng(55.933916, 2.237459);
        final LatLng cameraPosition = new LatLng(53.3499986, -1.83333);

        mGoogleMap.setLatLngBoundsForCameraTarget(new LatLngBounds(southWestBound, northEastBound));
        mGoogleMap.setMinZoomPreference(5.5f);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 6.6f));
    }

    public ArrayList<TrigPoint> readFromCsv() {
        InputStream is = getResources().openRawResource(R.raw.np_trigs);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is,
                Charset.forName("UTF-8")));

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
    public GoogleMap getmGoogleMap() {
        return mGoogleMap;
    }
}
