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
import android.widget.TextView;

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
    public VisitedMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.visited_menu_fragment, container, false);

        ArrayList<String> titles = new ArrayList<>();

        prefs = rootView.getContext().getSharedPreferences("MyPref", 0);
        editor = prefs.edit();
        Map<String,?> keys = prefs.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            titles.add(entry.getKey());
        }

        RecyclerView recyclerView = rootView.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getContext(), titles);
        recyclerView.setAdapter(adapter);

        TextView close = rootView.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        return rootView;
    }

}
