package com.uk.trigtracker.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.model.Circle;
import com.uk.trigtracker.R;
import com.uk.trigtracker.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisitedMenuFragment extends Fragment {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ArrayList<Circle> allMarkers;
    ArrayList<String> allTrigNames;
    ArrayList<String> visitedTrigNames;
    MapFragment mapFragment;
    Button selected;

    public VisitedMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.visited_menu_fragment, container, false);

        final ArrayList<String> titles = new ArrayList<>();
        visitedTrigNames = new ArrayList<>();

        prefs = rootView.getContext().getSharedPreferences("MyPref", 0);
        editor = prefs.edit();

        // Add all the visited points to the menu
        Map<String,?> keys = prefs.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            visitedTrigNames.add(entry.getKey());
        }

        final Button all = rootView.findViewById(R.id.showAll);
        final Button visited = rootView.findViewById(R.id.showVisited);

        final RecyclerView recyclerView = rootView.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getContext(), titles, this);
        adapter.setAllTitles(visitedTrigNames);
        selected = visited;
        recyclerView.setAdapter(adapter);

        final TextView close = rootView.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                close.setTextColor(getResources().getColor(R.color.visited));
                close.setBackgroundColor(getResources().getColor(R.color.visited));
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });


        all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adapter.setAllTitles(allTrigNames);
                recyclerView.setAdapter(adapter);
                all.setBackgroundResource(R.drawable.menu_button_all_selected);
                visited.setBackgroundResource(R.drawable.menu_button_visited_unselected);
                selected = all;
            }
        });

        visited.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adapter.setAllTitles(visitedTrigNames);
                recyclerView.setAdapter(adapter);
                visited.setBackgroundResource(R.drawable.menu_button_visited_selected);
                all.setBackgroundResource(R.drawable.menu_button_all_unselected);
                selected = visited;
            }
        });

        final SearchView searchView = rootView.findViewById(R.id.searchBar);


        // Makes entire search bar clickable, instead of just icon
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<String> results = new ArrayList<>();

                ArrayList<String> searchThrough = selected.getId() == R.id.showVisited ? visitedTrigNames : allTrigNames;

                for(String s : searchThrough) {
                    if(s.toLowerCase().contains(query.toLowerCase())) {
                        results.add(s);
                    }
                }

                if(results.size() == 0) {
                    results.add("No results found :(");
                }

                adapter.setAllTitles(results);
                recyclerView.setAdapter(adapter);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                adapter.setAllTitles(selected.getId() == R.id.showVisited ? visitedTrigNames : allTrigNames);

                recyclerView.setAdapter(adapter);

                return false;
            }
        });


        return rootView;
    }

    public void setAllMarkers(ArrayList<Circle> allMarkers) {
        this.allMarkers = allMarkers;
    }

    public ArrayList<Circle> getAllMarkers() {
        return this.allMarkers;
    }

    public MapFragment getMapFragment() {
        return mapFragment;
    }

    public void setMapFragment(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    public void setAllTrigNames(ArrayList<String> allTrigNames) {
        this.allTrigNames = allTrigNames;
    }

}
